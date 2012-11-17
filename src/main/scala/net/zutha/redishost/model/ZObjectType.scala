package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZObjectType extends ZObjectFactory[ZObjectType, IObjectType, MObjectType] {
  def typeName = "ZObjectType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZObjectType
  extends ZType
{
	type T <: ZObjectType
}

trait IObjectType
  extends ZObjectType
  with IType
{
	type T <: IObjectType
}

trait MObjectType
  extends ZObjectType
  with MType
{
	type T <: MObjectType
}
