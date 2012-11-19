package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldset._
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._

trait MutableReadQueries extends ReadQueries { self: MutableAccessor =>

  def objectIsNew( objKey: String ): Boolean = {
    redis.hexists( objHashKey( objKey ), objIsNewHKey )
  }
  override def getObjectZids( objKey: String ): Set[Zid] = {
    super.getObjectZids( objKey ) union dbAcc.getObjectZids( objKey )
  }

  def getObjectMergedZids( objKey: String ): Set[Zid] = {
    redis.smembers[Zid]( objMergedZidsKey(objKey) ).get.map(_.get)
  }

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

  def getObject( id: ZIdentity, fieldLimit: Int = 0 ): Option[MObject] = {
    val obj = id match {
      case MZids(pZids, allZids) => dbAcc.getObject(pZids.head) //TODO merge results
      case Zids(zid, allZids) => dbAcc.getObject(zid)
      case TempId(uuid) => None
    }

    ???
  }

  def getObjectT[T <: MObject]( id: ZIdentity, fieldLimit: Int = 0 ): Option[T] = {
    getObject(id, fieldLimit) map (_.asInstanceOf[T])
  }

  def getItem[T <: MItem]( item: MRef[T], fieldLimit: Int = 0): T = {
    getObject(item.id, fieldLimit).get.asInstanceOf[T]
  }

  def getField[T <: MField]( field: MRef[T], fieldLimit: Int = 0 ): T = {
    getObject(field.id, fieldLimit).get.asInstanceOf[T]
  }

  // TODO: implement stub
  def getRolePlayersOfField( field: MRef[MField] ): MRolePlayerSet = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField( field: MRef[MField] ): MLiteralSet = {
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


}
