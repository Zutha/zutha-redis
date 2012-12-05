package net.zutha.redishost

import db.DB
import schema.SchemaBuilder

object TestApp extends App {

  implicit val acc = DB.getMutableAccessor
  SchemaBuilder.build()
}

