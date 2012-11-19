package net.zutha.redishost.schema

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.builder.{SchemaHelpers, Helpers}

object SchemaBuilder {
  def build()( implicit acc: MutableAccessor ) {
    new SchemaBuilder
  }
}

class SchemaBuilder private ( implicit val acc: MutableAccessor )
  extends SchemaHelpers
  with ItemClasses
