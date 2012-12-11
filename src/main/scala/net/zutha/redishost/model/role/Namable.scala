package net.zutha.redishost.model.role

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.singleton.ZRoleSingleton

object Namable extends ZRoleSingleton {
  type ObjT = ZTrait with ZRole
  type ObjTM = MTrait with MRole
  type ObjTI = ITrait with IRole

  def name = "Namable"
}
