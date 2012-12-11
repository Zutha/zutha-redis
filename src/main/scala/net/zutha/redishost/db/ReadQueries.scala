package net.zutha.redishost.db

import net.zutha.redishost.model._
import datatype.{URI, ZString}
import fieldclass.ZField
import itemclass._
import literaltype.Name
import net.zutha.redishost.lib.ReflectionHelpers._
import singleton.ZObjectTypeSingleton
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{currentMirror => mirror}

trait ReadQueries[+U <: ZObject] { self: Accessor[U] =>

  // =================== Object Getters ======================

  def lookupObjectKeyByPSI( psi: URI ): Option[String] = redis.hget( psiHashKey, psi.asString )

  def lookupObjectKeyByName( name: ZString ): Option[String] = redis.hget( nameHashKey, Name.indexForm(name) )

  def getObjectZids( objKey: String ): Seq[Zid] = {
    redis.smembers[Zid]( objZidsKey(objKey) ).get.map(_.get).toSeq.sorted
  }

  def getObjectRef( key: String ): Option[Ref[U, ZObject]]

  def getObjectByKey( key: String): Option[ZObject]

  def getItemByKey( key: String ): Option[ZItem]

  def getFieldByKey( key: String ): Option[ZField]

  protected[redishost] def getTypedRef[T <: U: TypeTag]( key: String ): Option[Ref[U, T]] = {
    def wrongType: Nothing = {
      val tString = typeToString( typeOf[T] )
      throw new IllegalArgumentException( s"Object with key $key does not satisfy the type: $tString" )
    }

    def getZTypeFromTypeName( typeName: String ): RefU[U, ZObjectType] = {
      val classSym =  mirror.staticClass( typeName )
      val companionModSym =  classSym.companionSymbol.asModule
      val companionClassSym = companionModSym.moduleClass.asClass
      val companion = if ( companionClassSym.baseClasses.contains( typeOf[ZObjectTypeSingleton].typeSymbol ) ){
        mirror.reflectModule( companionModSym ).instance.asInstanceOf[ZObjectTypeSingleton]
      } else {
        throw new IllegalArgumentException(
          "type parameter T must include only scala types that are defined in the Zutha Schema" )
      }
      val zType: RefU[U, ZObjectType] = companion.ref[U, ZObjectType with U]
      zType
    }

    def validateType( obj: Ref[U, ZObject] ): Boolean = {
      def objHasZType( typeName: String ): Boolean = {
        val zType = getZTypeFromTypeName( typeName )
        objectHasType(obj, zType)
      }

      typeOf[T] match {
        case RefinedType(parents, decls) => parents.forall{ p =>
          objHasZType( p.typeSymbol.fullName )
        }
        case SingleType(pre, sym) => objHasZType( sym.fullName )
      }
    }

    getObjectRef( key ) map { obj =>
      if ( validateType( obj ) ){
        obj.asInstanceOf[Ref[U, T]]
      } else {
        wrongType
      }
    }
  }


  // =================== Generic Queries ======================

  def objectHasType( obj: Ref[U, ZObject],
                     zType: Ref[U, ZObjectType] ): Boolean


  // =================== Object Member Getters ======================


}
