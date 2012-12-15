package net.zutha.redishost.db

import net.zutha.redishost.model._
import datatype.{URI, ZString}
import fieldset.ZFieldSet
import itemclass._
import literaltype.Name
import singleton.ZSingleton
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{currentMirror => mirror}
import special.ZNothing
import net.zutha.redishost.model.ScopeMatchType._
import net.zutha.redishost.model.ZRef

trait ReadQueries[A <: Accessor[A]] { self: A =>

  // =================== Object Getters ======================

  def lookupObjectKeyByPSI( psi: URI ): Option[String] = redis.hget( psiHashKey, psi.asString )

  def lookupObjectKeyByName( name: ZString ): Option[String] = redis.hget( nameHashKey, Name.indexForm(name) )

  def getTypedRef[T >: ZNothing <: ZObject: TypeTag]( key: String ): Option[ZRef[A, T]] = {

    def getZTypeKeyFromScalaClassName( className: String ): String = {
      val classSym =  mirror.staticClass( className )
      val companionModuleSym =  classSym.companionSymbol.asModule
      val companionClassSym = companionModuleSym.moduleClass.asClass
      val companion = if ( companionClassSym.baseClasses.contains( typeOf[ZSingleton[ZObjectType]].typeSymbol ) ){
        mirror.reflectModule( companionModuleSym ).instance.asInstanceOf[ZSingleton[ZObjectType]]
      } else throw new IllegalArgumentException(
        "type parameter T must include only scala traits that have a ZSingleton companion object" )
      val zTypeKey: String = companion.key[A]
      zTypeKey
    }

    def validateType( objKey: String ): Boolean = {
      typeOf[T] match {
        case RefinedType(parents, decls) => {
          val zTypeKeys = parents.map{ p =>
            getZTypeKeyFromScalaClassName( p.typeSymbol.fullName )
          }
          objHasTypes( objKey, zTypeKeys )
        }
        case SingleType(pre, sym) => objHasType( objKey, sym.fullName )
      }
    }

    correctKey( key ) match {
      case Some( objKey ) if validateType( objKey ) => Some( ZRef[A, T]( objKey ) )
      case _ => None
    }
  }

  protected[redishost] def loadObject[
    Impl <: ZObject : TypeTag,
    ZT >: ZNothing <: ZObject : TypeTag
  ]
  ( zRef: ZRef[A, ZT] ): Obj[Impl, ZT] = {
    ???
  }


  // =================== Generic Queries ======================

  protected[db] def correctKey( key: String ): Option[String] = key match {
    case Zid(zid) => getObjectZids( key ).toSeq match {
      case Seq() => None
      case zids => Some( zids.head.key )
    }
    case _ => None
  }

  protected[db] def objHasTypes( objKey: String, zTypeKeys: Iterable[String] ): Boolean = ???
  protected[db] def objHasType( objKey: String, zTypeKey: String ): Boolean = ???

  def objectHasType( obj: ZRef[A, ZObject],
                     zType: ZRef[A, ZObjectType] ): Boolean = {
    ???
  }


  // =================== Object Member Getters ======================

  def getObjectZids( objKey: String ): Seq[Zid] = {
    redis.smembers[Zid]( objZidsKey(objKey) ).get.map(_.get).toSeq.sorted
  }

  //  TODO: implement stub
  def getFieldSet( parent: ZRef[A, ZObject],
                   role: ZRef[A, ZRole],
                   fieldClass: ZRef[A, ZFieldClass],
                   scopeFilter: ScopeSeq[A],
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int
                   ): ZFieldSet[A]

}
