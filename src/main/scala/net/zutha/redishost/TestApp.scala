package net.zutha.redishost

import db.DB
import model._
import model.itemclass.{ZTrait, ZRole}
import schema.SchemaBuilder

object TestApp extends App {

  implicit val acc = DB.getMutableAccessor
  SchemaBuilder.build()

  val roleTrait: MRef[ZTrait with ZRole] = ???
  val roleTrait2: ZTrait with ZRole =  roleTrait.load

  val ref = ZRef[MA, ZRole with ZTrait]( "" )
  val role1 =  ref.load
  val tref1: ZRef[MA, ZRole] = role1.zRef
}

