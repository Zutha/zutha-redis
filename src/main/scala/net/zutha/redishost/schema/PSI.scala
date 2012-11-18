package net.zutha.redishost.schema

import net.zutha.redishost.model._

object PSI extends SchemaObject {
  type ThisT = ZLiteralType
  type ThisI = ILiteralType
  type ThisM = MLiteralType

  def name = "PSI"
}