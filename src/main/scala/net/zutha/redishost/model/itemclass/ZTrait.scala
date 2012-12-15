package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZItemClassSingleton, ZSingleton}

object ZTrait
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZTrait]
  with ZRoleSingleton
{

  def name = "Trait"

}

trait ZTrait
  extends ZObjectType
  with Referenceable[ZTrait]
