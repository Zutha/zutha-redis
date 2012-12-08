package net.zutha.redishost.model.`trait`

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.companion.ZRoleCompanion

object FieldDeclarer extends ZRoleCompanion {
  type ObjT = ZTrait with ZRole
  type ObjTM = MTrait with MRole
  type ObjTI = ITrait with IRole

  def name = "Field Declarer"
}
