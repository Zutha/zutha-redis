package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZScopeType extends ZItemClassCompanion[ZScopeType, IScopeType, MScopeType] {

  def name = "ScopeType"


}

trait ZScopeType
  extends ZType
{
	type T <: ZScopeType
}

trait IScopeType
  extends ZScopeType
  with IType
{
	type T <: IScopeType
}

trait MScopeType
  extends ZScopeType
  with MType
{
	type T <: MScopeType
}
