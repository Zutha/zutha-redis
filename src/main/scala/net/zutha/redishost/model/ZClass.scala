package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZClass extends ZObjectFactory[ZClass, IClass, MClass] {
  def typeName = "ZClass"

  def validType_?(obj: ZObject): Boolean = ???
}
trait ZClass
  extends ZType
{
	type T <: ZClass
}

trait IClass[A <: ImmutableAccessor]
  extends ZClass
  with IType[A]
{
	type T <: IClass[A]
}

trait MClass[A <: MutableAccessor]
  extends ZClass
  with MType[A]
{
	type T <: MClass[A]
}
