package net.zutha.redishost.model.role

import net.zutha.redishost.model.itemclass.{IRole, MRole, ZRole}
import net.zutha.redishost.model.companion.ZRoleCompanion

object Namable extends ZRoleCompanion {
  type ObjT = ZRole
  type ObjTM = MRole
  type ObjTI = IRole

  def name = "Namable"
}
