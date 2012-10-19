package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZEntity extends ZObjectFactory[ZEntity, IEntity, MEntity] {
  def typeName = "ZEntity"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZEntity
  extends ZItem
{
	type T <: ZEntity
}

trait IEntity[A <: ImmutableAccessor]
  extends IItem[A]
  with ZEntity
{
	type T <: IEntity[A]
}

trait MEntity[A <: MutableAccessor]
  extends ZEntity
  with MItem[A]
{
	type T <: MEntity[A]
}
