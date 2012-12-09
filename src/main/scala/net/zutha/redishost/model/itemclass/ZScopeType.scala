package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZScopeType extends ZItemClassCompanion[ZScopeType, IScopeType, MScopeType] {

  def name = "ScopeType"


}

trait ZScopeType
  extends ZType
  with ZItemLike[ZScopeType]

trait IScopeType
  extends ZScopeType
  with IType
  with IItemLike[IScopeType]

trait MScopeType
  extends ZScopeType
  with MType
  with MItemLike[MScopeType]
