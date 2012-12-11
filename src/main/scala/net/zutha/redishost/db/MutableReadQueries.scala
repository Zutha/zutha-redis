package net.zutha.redishost.db

import net.zutha.redishost.model._
import singleton.ZObjectTypeSingleton
import fieldclass._
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import net.zutha.redishost.lib.ReflectionHelpers._
import scala.reflect.runtime.universe._
import scala.reflect.runtime.{currentMirror => mirror}

trait MutableReadQueries extends ReadQueries { self: MutableAccessor =>

  // =================== Object Getters ======================

  def getObjectRef( key: String ): Option[MRef[MObject]] = key match {
    case Zid(zid) => getObjectZids( key ).toSeq match {
      case Seq() => dbAcc.getObjectZids( key ) match {
        case Seq() => None
        case zids => Some(MRef(Zids(zid, zids.toSeq.sorted)))
      }
      case zids => getObjectMergedZids( key ) match {
        case Seq() => Some(MRef(Zids(zid, zids.toSeq.sorted)))
        case mZids => Some(MRef(MZids(mZids.toSeq.sorted, zids.toSeq.sorted)))
      }
    }
    case tempId => if ( objectIsNew( tempId ) )
      Some(MRef(TempId( tempId )))
    else
      None
  }

  def getObjectByKey( key: String): Option[MObject] = {
    // check if this object is an item or field and then delegate to getItem../getField..
    ???
  }

  def getItemByKey( key: String ): Option[MItem] = {
    ???
  }

  def getFieldByKey( key: String ): Option[MField] = {
    ???
  }

  def getObjectById( id: ZIdentity ): Option[MObject] = {
    val obj = id match {
      case MZids(pZids, allZids) => dbAcc.getObjectByKey(pZids.head.key) //TODO merge results
      case Zids(zid, allZids) => dbAcc.getObjectByKey(zid.key)
      case TempId(idStr) => None
    }

    ???
  }


  protected[redishost] def getTypedRef[T <: MObject: TypeTag]( key: String ): Option[MRef[T]] = {
    def wrongType: Nothing = {
      val tString = typeToString( typeOf[T] )
      throw new IllegalArgumentException( s"Object with key $key does not satisfy the type: $tString" )
    }

    def getZTypeFromTypeName( typeName: String ): MRef[MObjectType] = {
      val classSym =  mirror.staticClass( typeName )
      val companionModSym =  classSym.companionSymbol.asModule
      val companionClassSym = companionModSym.moduleClass.asClass
      val companion = if ( companionClassSym.baseClasses.contains( typeOf[ZObjectTypeSingleton].typeSymbol ) ){
        mirror.reflectModule( companionModSym ).instance.asInstanceOf[ZObjectTypeSingleton]
      } else {
        throw new IllegalArgumentException(
          "type parameter T must include only scala types that are defined in the Zutha Schema" )
      }
      val zType: MRef[MObjectType] = companion.refM
      zType
    }

    def validateType( obj: MRef[MObject] ): Boolean = {
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
        obj.asInstanceOf[MRef[T]]
      } else {
        wrongType
      }
    }
  }


  protected[redishost] def getTypedObjectByKey[T <: MObject: TypeTag]( key: String ): Option[T] = {
    getTypedRef(key).map(_.load)
  }

  def reloadObject[T <: MObject: TypeTag]( obj: MRef[T] ): T = {
    getObjectByKey( obj.key ).get.asInstanceOf[T]
  }

  def reloadItem[T <: MItem: TypeTag]( item: MRef[T] ): T = {
    getItemByKey( item.key ).get.asInstanceOf[T]
  }

  def reloadField[T <: MField: TypeTag]( field: MRef[T]  ): T = {
    getFieldByKey( field.key ).get.asInstanceOf[T]
  }

  // =================== Object Member Getters ======================

  override def getObjectZids( key: String ): Set[Zid] = {
    super.getObjectZids( key ) union dbAcc.getObjectZids( key )
  }

  def getObjectMergedZids( key: String ): Set[Zid] = {
    redis.smembers[Zid]( objMergedZidsKey(key) ).get.map(_.get)
  }

  // TODO: implement stub
  def getRolePlayersOfField( field: MRef[MField] ): Set[MRolePlayer] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField( field: MRef[MField] ): MLiteralMap = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: MRef[MObject],
                   role: MRef[MRole],
                   fieldClass: MRef[MFieldClass],
                   scopeFilter: MScopeSeq,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int,
                   includeDeleted_? : Boolean
                   ): MFieldSet = {
    val fields: MFieldSeq = ???
    val messages = Seq()
    MFieldSet( parent, role, fieldClass, fields, scopeFilter, scopeMatchType,
      order, limit, offset, includeDeleted_?, messages )
  }

  // TODO: implement stub
  def getUpdatedItem( item: MRef[ModifiedItem] ): ModifiedItem = ???

  // TODO: implement stub
  def getUpdatedField( field: MRef[ModifiedField] ): ModifiedField = ???


  // =================== Queries ======================

  def objectIsNew( key: String ): Boolean = {
    redis.hexists( objHashKey( key ), objIsNewHKey )
  }

  def objectHasType( obj: MRef[MObject], zType: MRef[MObjectType] ): Boolean = {
    ???
  }

}
