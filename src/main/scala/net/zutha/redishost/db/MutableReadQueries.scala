package net.zutha.redishost.db

import net.zutha.redishost.model._
import collection.GenMap
import net.zutha.redishost.model.Zids
import net.zutha.redishost.model.TempId
import net.zutha.redishost.model.Zid

trait MutableReadQueries extends ReadQueries { self: MutableAccessor =>

  protected def dbAcc: ImmutableAccessor

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZObject] = ???

  // QUESTION why doesn't this override definition in ReadQueries?
  def getObject(id: ZIdentity, fieldLimit: Int = 0): Option[MObject] = {
    val obj = id match {
      case Zids(pZids, allZids) => dbAcc.getObject(pZids.head) //TODO merge results
      case zid: Zid => dbAcc.getObject(zid)
      case TempId(uuid) => None
    }

    ???
  }


  def getItem[T <: MItem]( item: T, fieldLimit: Int = 0): T = {
    getObject(item.id, fieldLimit).get.asInstanceOf[T]
  }

  def getField[T <: MField]( field: T, fieldLimit: Int = 0 ): T = {
    getObject(field.id, fieldLimit).get.asInstanceOf[T]
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: MField): MRolePlayerSet = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: MField): MLiteralSet = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: MObject,
                   role: MRole,
                   fieldClass: MFieldClass,
                   limit: Int,
                   offset: Int,
                   includeDeleted_? : Boolean
                   ): MFieldSet = {
    val fields: MFieldMap = ???
    MFieldSet( this, parent.ref, role.ref, fieldClass.ref, fields, limit, offset, includeDeleted_?)
  }

  // TODO: implement stub
  def getUpdatedItem( item: ModifiedItem, fieldLimit: Int = 0): ModifiedItem = ???

  // TODO: implement stub
  def getUpdatedField( field: ModifiedField, fieldLimit: Int = 0 ): ModifiedField = ???

  // TODO: implement with single redis request
  def getUpdatedFields( fields: ModifiedField* ): MFieldMap = {
    fields.map{ f =>
      val newf = getUpdatedField(f)
      (newf.id -> newf)
    }.toMap
  }


}
