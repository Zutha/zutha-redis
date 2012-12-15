package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZEntity
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZEntity]
  with ZRoleSingleton
{

  def name = "Entity"

}

trait ZEntity
  extends ZItem
  with Referenceable[ZEntity]
