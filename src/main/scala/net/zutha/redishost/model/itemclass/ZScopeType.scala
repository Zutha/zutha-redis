package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZScopeType
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZScopeType]
  with ZRoleSingleton
{

  def name = "ScopeType"
}

trait ZScopeType
  extends ZType
  with Referenceable[ZScopeType]
