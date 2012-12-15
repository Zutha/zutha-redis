package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZObjectType
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZObjectType]
  with ZRoleSingleton
{

  def name = "ObjectType"

}

trait ZObjectType
  extends ZType
  with Referenceable[ZObjectType]
