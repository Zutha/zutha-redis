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

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZConcreteObject] = ???

  // QUESTION why doesn't this override definition in ReadQueries?
  def getObject(id: ZIdentity, fieldLimit: Int = 0): Option[ZConcreteMutableObject[A0]] = {
    val obj = id match {
      case Zids(pZids, allZids) => dbAcc.getObject(pZids.head) //TODO merge results
      case zid: Zid => dbAcc.getObject(zid)
      case TempId(uuid) => None
    }

    ???
  }


  def getItem[T <: ZMutableItem[A0]]( item: T, fieldLimit: Int = 0): T = {
    getObject(item.id, fieldLimit).get.asInstanceOf[T]
  }

  def getField[A >: A0 <: MutableAccessor, T <: ZMutableField[A0]]( field: T, fieldLimit: Int = 0 ): T = {
    getObject(field.id, fieldLimit).get.asInstanceOf[T]
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: ZMutableField[A0]): MRolePlayerSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: ZMutableField[A0]): MLiteralSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: ZMutableObject[A0],
                   role: ZMRole[A0],
                   fieldClass: ZMFieldClass[A0],
                   limit: Int,
                   offset: Int,
                   includeDeleted_? : Boolean
                   ): ZMutableFieldSet[A0] = {
    val fields: MFieldMap[A0] = ???
    ZMutableFieldSet[A0]( me, parent.ref, role.ref, fieldClass.ref, fields, limit, offset, includeDeleted_?)
  }

  // TODO: implement stub
  def getUpdatedItem( item: ZModifiedItem[A0], fieldLimit: Int = 0): ZModifiedItem[A0] = ???

  // TODO: implement stub
  def getUpdatedField( field: ZModifiedField[A0], fieldLimit: Int = 0 ): ZModifiedField[A0] = ???

  // TODO: implement with single redis request
  def getUpdatedFields( fields: ZModifiedField[A0]* ): MFieldMap[A0] = {
    fields.map{ f =>
      val newf = getUpdatedField(f)
      (newf.id -> newf)
    }.toMap
  }


}
