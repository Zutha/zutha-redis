package net.zutha.redishost.model.role

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.singleton.{ZSingleton, ZRoleSingleton}

object Namable
  extends ZSingleton[ZTrait with ZRole]
  with ZRoleSingleton
{
  def name = "Namable"
}
