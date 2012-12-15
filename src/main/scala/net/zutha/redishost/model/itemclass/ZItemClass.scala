package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZItemClass
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZItemClass]
  with ZRoleSingleton
{
  def name = "ItemClass"

}

trait ZItemClass
  extends ZClass
  with Referenceable[ZItemClass]
