package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZScopeType extends ZObjectFactory[ZScopeType, IScopeType, MScopeType] {
  def typeName = "ZScopeType"

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
