package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZTrait extends ZObjectFactory[ZTrait, ITrait, MTrait] {
  def typeName = "ZTrait"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZTrait
  extends ZObjectType
{
	type T <: ZTrait
}

trait ITrait
  extends ZTrait
  with IObjectType
{
	type T <: ITrait
}

trait MTrait
  extends ZTrait
  with MObjectType
{
	type T <: MTrait
}
