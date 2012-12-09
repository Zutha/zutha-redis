package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import net.zutha.redishost.exception.SchemaException
import scala.reflect.runtime.universe._
import net.zutha.redishost.lib.ReflectionHelpers._

trait MutableReadQueries extends ReadQueries { self: MutableAccessor =>

  // =================== Object Getters ======================

  def getObjectRef( objKey: String ): Option[MRef[MObject]] = objKey match {
    case Zid(zid) => getObjectZids( objKey ).toSeq match {
      case Seq() => dbAcc.getObjectZids( objKey ) match {
        case Seq() => None
        case zids => Some(MRef(Zids(zid, zids.toSeq.sorted)))
      }
      case zids => getObjectMergedZids( objKey ) match {
        case Seq() => Some(MRef(Zids(zid, zids.toSeq.sorted)))
        case mZids => Some(MRef(MZids(mZids.toSeq.sorted, zids.toSeq.sorted)))
      }
    }
    case tempId => if ( objectIsNew( tempId ) )
      Some(MRef(TempId( tempId )))
    else
      None
  }

  def getObjectRaw( objKey: String, fieldLimit: Int = 0 ): Option[ZObject] = {
    ???
  }

  def getObjectById( id: ZIdentity, fieldLimit: Int = 0 ): Option[MObject] = {
    val obj = id match {
      case MZids(pZids, allZids) => dbAcc.getObject(pZids.head) //TODO merge results
      case Zids(zid, allZids) => dbAcc.getObject(zid)
      case TempId(idStr) => None
    }

    ???
  }

  protected def getItemById( id: ZIdentity, fieldLimit: Int = 0 ): Option[MItem] = {
    ???
  }
  protected def getFieldById( id: ZIdentity, fieldLimit: Int = 0 ): Option[MField] = {
    ???
  }

  protected def getTypedObjectById[T <: MObject: TypeTag]( id: ZIdentity, fieldLimit: Int = 0 ): Option[T] = {
    def wrongType: Nothing = {
      val tString = typeToString( typeOf[T] )
      throw new IllegalArgumentException( s"Object with id $id does not satisfy the type: $tString" )
    }

    getObjectRef( id.key ) flatMap { obj =>
      // If Object is an Item
      if ( objectHasType( obj, ZItem.refM ) ) {
        if ( typeOf[T] <:< typeOf[MItem] || typeOf[MItem] <:< typeOf[T] ){
          val item: Option[MItem] = getItemById( id, fieldLimit )
          item map (_.asInstanceOf[T])
        }
        else wrongType
      }
      // If Object is a Field
      else if (objectHasType( obj, ZField.refM ) ) {
        if ( typeOf[T] <:< typeOf[MField] || typeOf[MField] <:< typeOf[T] ){
          val field: Option[MField] = getFieldById( id, fieldLimit )
          field map (_.asInstanceOf[T])
        }
        else wrongType
      }
      // Object is neither an Item nor a Field
      else throw new SchemaException(
          s"Every object must be either an Item or a Field. Object with id: $id is neither." )
    }
  }

  def reloadObject[T <: MObject: TypeTag]( obj: MRef[T], fieldLimit: Int = 0): T = {
    getTypedObjectById(obj.id, fieldLimit).get
  }

  def reloadItem[T <: MItem: TypeTag]( item: MRef[T], fieldLimit: Int = 0): T = {
    getTypedObjectById(item.id, fieldLimit).get
  }

  def reloadField[T <: MField: TypeTag]( field: MRef[T], fieldLimit: Int = 0 ): T = {
    getTypedObjectById(field.id, fieldLimit).get
  }

  // =================== Object Member Getters ======================

  override def getObjectZids( objKey: String ): Set[Zid] = {
    super.getObjectZids( objKey ) union dbAcc.getObjectZids( objKey )
  }

  def getObjectMergedZids( objKey: String ): Set[Zid] = {
    redis.smembers[Zid]( objMergedZidsKey(objKey) ).get.map(_.get)
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
  def getUpdatedItem( item: MRef[ModifiedItem], fieldLimit: Int = 0 ): ModifiedItem = ???

  // TODO: implement stub
  def getUpdatedField( field: MRef[ModifiedField], fieldLimit: Int = 0 ): ModifiedField = ???


  // =================== Queries ======================

  def objectIsNew( objKey: String ): Boolean = {
    redis.hexists( objHashKey( objKey ), objIsNewHKey )
  }

  def objectHasType( obj: MRef[MObject], zType: MRef[MType] ): Boolean = {
    ???
  }

}
