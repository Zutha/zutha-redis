package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZItemClassSingleton, ZSingleton}

object ZType
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZType]
  with ZRoleSingleton
{

  def name = "Type"

}

trait ZType
  extends ZItem
  with Referenceable[ZType]
