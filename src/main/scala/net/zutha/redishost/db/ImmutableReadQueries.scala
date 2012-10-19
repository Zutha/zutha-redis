package net.zutha.redishost.db

import net.zutha.redishost.model._
import scala.Predef._
import net.zutha.redishost.model.ZImmutableItem
import net.zutha.redishost.model.ZImmutableField
import net.zutha.redishost.model.Zid

trait ImmutableReadQueries extends ReadQueries {
  type A0 <: ImmutableAccessor
  def me: A0

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZConcreteObject] = ???

  def getObject(id: Zid, fieldLimit: Int = 0): Option[ZConcreteImmutableObject[A0]] = {
    ???
  }

  def getItem( item: ZImmutableItem[A0], fieldLimit: Int = 0): ZImmutableItem[A0] = {
    getObject(item.id, fieldLimit).asInstanceOf[ZImmutableItem[A0]]
  }

  def getField( field: ZImmutableField[A0], fieldLimit: Int = 0 ): ZImmutableField[A0] = {
    getObject(field.id, fieldLimit).asInstanceOf[ZImmutableField[A0]]
  }

  // TODO: implement with single redis request
  def getFields( fields: ZImmutableField[A0]* ): IFieldMap[A0] = {
    fields.map{ f =>
      val newf = getField(f)
      (newf.id -> newf)
    }.toMap
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: ZImmutableField[A0]): IRolePlayerSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: ZImmutableField[A0]): ILiteralSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: ZImmutableObject[A0],
                   role: ZIRole[A0],
                   fieldClass: ZIFieldClass[A0],
                   limit: Int,
                   offset: Int
                   ): ZImmutableFieldSet[A0] = {
    val fields: IFieldMap[A0] = ???
    ZImmutableFieldSet( me, parent.ref, role.ref, fieldClass.ref, fields, limit, offset )
  }




}
