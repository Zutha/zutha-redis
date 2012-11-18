package net.zutha.redishost.model

object ZScopeType extends ZItemClassCompanion[ZScopeType, IScopeType, MScopeType] {

  def name = "ZScopeType"

  def validType_?(obj: ZObject): Boolean = ???
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
