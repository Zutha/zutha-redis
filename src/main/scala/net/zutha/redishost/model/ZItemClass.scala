package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZItemClass extends ZObjectFactory[ZItemClass, IItemClass, MItemClass] {
  def typeName = "ZItemClass"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZItemClass
  extends ZClass
{
	type T <: ZItemClass
}

trait IItemClass[A <: ImmutableAccessor]
  extends ZItemClass
  with IClass[A]
{
	type T <: IItemClass[A]
}

trait MItemClass[A <: MutableAccessor]
  extends ZItemClass
  with MClass[A]
{
	type T <: MItemClass[A]
}
