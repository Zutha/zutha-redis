package net.zutha.redishost.db

import net.zutha.redishost.model._
import scala.Predef._
import net.zutha.redishost.model.IItem
import net.zutha.redishost.model.IField
import net.zutha.redishost.model.Zid

trait ImmutableReadQueries extends ReadQueries { self: ImmutableAccessor =>

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZObject] = ???

  def getObject(id: Zid, fieldLimit: Int = 0): Option[IObject] = {
    ???
  }

  def getObjectT[T <: IObject](id: Zid, fieldLimit: Int = 0): Option[T] = {
    getObject(id, fieldLimit) map (_.asInstanceOf[T])
  }

  def getItem( item: IRef[IItem], fieldLimit: Int = 0): IItem = {
    getObject(item.zid, fieldLimit).get.asInstanceOf[IItem]
  }

  def getField( field: IRef[IField], fieldLimit: Int = 0 ): IField = {
    getObject(field.zid, fieldLimit).get.asInstanceOf[IField]
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: IRef[IField]): IRolePlayerSet = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: IRef[IField]): ILiteralSet = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: IRef[IObject],
                   role: IRef[IRole],
                   fieldClass: IRef[IFieldClass],
                   limit: Int,
                   offset: Int
                   ): IFieldSet = {
    val fields: IFieldMap = ???
    IFieldSet( this, parent, role, fieldClass, fields, limit, offset )
  }




}
