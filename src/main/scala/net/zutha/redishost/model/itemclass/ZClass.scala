package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZClass
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZClass]
  with ZRoleSingleton
{

  def name = "Class"

}

trait ZClass
  extends ZObjectType
  with Referenceable[ZClass]
