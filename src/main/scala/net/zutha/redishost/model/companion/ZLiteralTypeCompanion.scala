package net.zutha.redishost.model.companion

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZLiteralTypeCompanion
  extends SchemaItem
{
  type ObjC = ZLiteralType
  type ObjCM = MLiteralType
  type ObjCI = ILiteralType

  type ObjT = ZLiteralType
  type ObjTM = MLiteralType
  type ObjTI = ILiteralType

  def classFactory = ZLiteralType
}
