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

  def getItem( item: IItem, fieldLimit: Int = 0): IItem = {
    getObject(item.id, fieldLimit).asInstanceOf[IItem]
  }

  def getField( field: IField, fieldLimit: Int = 0 ): IField = {
    getObject(field.id, fieldLimit).asInstanceOf[IField]
  }

  // TODO: implement with single redis request
  def getFields( fields: IField* ): IFieldMap = {
    fields.map{ f =>
      val newf = getField(f)
      (newf.id -> newf)
    }.toMap
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: IField): IRolePlayerSet = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: IField): ILiteralSet = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: IObject,
                   role: IRole,
                   fieldClass: IFieldClass,
                   limit: Int,
                   offset: Int
                   ): IFieldSet = {
    val fields: IFieldMap = ???
    IFieldSet( this, parent.ref, role.ref, fieldClass.ref, fields, limit, offset )
  }




}
