package net.zutha.redishost.db

import net.zutha.redishost.model._
import collection.GenMap
import net.zutha.redishost.model.Zids
import net.zutha.redishost.model.TempId
import net.zutha.redishost.model.Zid

trait MutableReadQueries extends ReadQueries {

  type A0 <: MutableAccessor
  def me: A0

  protected def dbAcc: ImmutableAccessor

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZObject] = ???

  // QUESTION why doesn't this override definition in ReadQueries?
  def getObject(id: ZIdentity, fieldLimit: Int = 0): Option[MObject[A0]] = {
    val obj = id match {
      case Zids(pZids, allZids) => dbAcc.getObject(pZids.head) //TODO merge results
      case zid: Zid => dbAcc.getObject(zid)
      case TempId(uuid) => None
    }

    ???
  }


  def getItem[T <: MItem[A0]]( item: T, fieldLimit: Int = 0): T = {
    getObject(item.id, fieldLimit).get.asInstanceOf[T]
  }

  def getField[A >: A0 <: MutableAccessor, T <: MField[A0]]( field: T, fieldLimit: Int = 0 ): T = {
    getObject(field.id, fieldLimit).get.asInstanceOf[T]
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: MField[A0]): MRolePlayerSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: MField[A0]): MLiteralSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: MObject[A0],
                   role: MRole[A0],
                   fieldClass: MFieldClass[A0],
                   limit: Int,
                   offset: Int,
                   includeDeleted_? : Boolean
                   ): MFieldSet[A0] = {
    val fields: MFieldMap[A0] = ???
    MFieldSet[A0]( me, parent.ref, role.ref, fieldClass.ref, fields, limit, offset, includeDeleted_?)
  }

  // TODO: implement stub
  def getUpdatedItem( item: ModifiedItem[A0], fieldLimit: Int = 0): ModifiedItem[A0] = ???

  // TODO: implement stub
  def getUpdatedField( field: ModifiedField[A0], fieldLimit: Int = 0 ): ModifiedField[A0] = ???

  // TODO: implement with single redis request
  def getUpdatedFields( fields: ModifiedField[A0]* ): MFieldMap[A0] = {
    fields.map{ f =>
      val newf = getUpdatedField(f)
      (newf.id -> newf)
    }.toMap
  }


}
