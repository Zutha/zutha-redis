package net.zutha.redishost.schema

import net.zutha.redishost.model._

object PSI extends SchemaItem {
  type ObjT = ZLiteralType
  type ObjTM = MLiteralType
  type ObjTI = ILiteralType

  def classFactory = ZLiteralType

  def name = "PSI"
}
