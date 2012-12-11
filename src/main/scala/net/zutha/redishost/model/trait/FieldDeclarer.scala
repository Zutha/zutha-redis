package net.zutha.redishost.model.`trait`

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.singleton.{ZTraitSingleton, ZRoleSingleton}

object FieldDeclarer extends ZTraitSingleton with ZRoleSingleton {
  type ObjT = ZTrait with ZRole
  type ObjTM = MTrait with MRole
  type ObjTI = ITrait with IRole

  def name = "Field Declarer"
}
