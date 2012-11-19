package net.zutha.redishost.model.companion

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZRoleCompanion
  extends SchemaItem
{
  type ObjC = ZRole
  type ObjCM = MRole
  type ObjCI = IRole

  def classFactory = ZRole
}
