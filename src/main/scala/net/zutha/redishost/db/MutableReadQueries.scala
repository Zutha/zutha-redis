package net.zutha.redishost.db

import net.zutha.redishost.model._
import net.zutha.redishost.model.ScopeMatchType._

trait MutableReadQueries extends ReadQueries { self: MutableAccessor =>

  protected def dbAcc: ImmutableAccessor

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZObject] = ???

  // QUESTION why doesn't this override definition in ReadQueries?
  def getObject(id: ZIdentity, fieldLimit: Int = 0): Option[MObject] = {
    val obj = id match {
      case MZids(pZids, allZids) => dbAcc.getObject(pZids.head) //TODO merge results
      case Zids(zid, allZids) => dbAcc.getObject(zid)
      case TempId(uuid) => None
    }

    ???
  }

  def getObjectT[T <: MObject](id: ZIdentity, fieldLimit: Int = 0): Option[T] = {
    getObject(id, fieldLimit) map (_.asInstanceOf[T])
  }

  def getItem[T <: MItem]( item: MRef[T], fieldLimit: Int = 0): T = {
    getObject(item.id, fieldLimit).get.asInstanceOf[T]
  }

  def getField[T <: MField]( field: MRef[T], fieldLimit: Int = 0 ): T = {
    getObject(field.id, fieldLimit).get.asInstanceOf[T]
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: MRef[MField]): MRolePlayerSet = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: MRef[MField]): MLiteralSet = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: MRef[MObject],
                   role: MRef[MRole],
                   fieldClass: MRef[MFieldClass],
                   scopeFilter: MScopeList,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int,
                   includeDeleted_? : Boolean
                   ): MFieldSet = {
    val fields: MFieldList = ???
    val messages = List()
    MFieldSet( this, parent, role, fieldClass, fields, scopeFilter, scopeMatchType, order, limit, offset, includeDeleted_?, messages)
  }

  // TODO: implement stub
  def getUpdatedItem( item: MRef[ModifiedItem], fieldLimit: Int = 0): ModifiedItem = ???

  // TODO: implement stub
  def getUpdatedField( field: MRef[ModifiedField], fieldLimit: Int = 0 ): ModifiedField = ???


}
