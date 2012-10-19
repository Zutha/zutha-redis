package net.zutha.redishost.db

import net.zutha.redishost.model._
import scala.Predef._
import net.zutha.redishost.model.IItem
import net.zutha.redishost.model.IField
import net.zutha.redishost.model.Zid

trait ImmutableReadQueries extends ReadQueries {
  type A0 <: ImmutableAccessor
  def me: A0

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZObject] = ???

  def getObject(id: Zid, fieldLimit: Int = 0): Option[IObject[A0]] = {
    ???
  }

  def getItem( item: IItem[A0], fieldLimit: Int = 0): IItem[A0] = {
    getObject(item.id, fieldLimit).asInstanceOf[IItem[A0]]
  }

  def getField( field: IField[A0], fieldLimit: Int = 0 ): IField[A0] = {
    getObject(field.id, fieldLimit).asInstanceOf[IField[A0]]
  }

  // TODO: implement with single redis request
  def getFields( fields: IField[A0]* ): IFieldMap[A0] = {
    fields.map{ f =>
      val newf = getField(f)
      (newf.id -> newf)
    }.toMap
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: IField[A0]): IRolePlayerSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: IField[A0]): ILiteralSet[A0] = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: IObject[A0],
                   role: IRole[A0],
                   fieldClass: IFieldClass[A0],
                   limit: Int,
                   offset: Int
                   ): IFieldSet[A0] = {
    val fields: IFieldMap[A0] = ???
    IFieldSet( me, parent.ref, role.ref, fieldClass.ref, fields, limit, offset )
  }




}
