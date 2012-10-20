package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZType extends ZObjectFactory[ZType, IType, MType] {
  def typeName = "ZType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZType
  extends ZItem
{
	type T <: ZType
}

trait IType
  extends ZType
  with IItem
{
	type T <: IType
}

trait MType
  extends ZType
  with MItem
{
	type T <: MType
}
