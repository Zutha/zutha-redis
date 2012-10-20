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

trait IClass
  extends ZClass
  with IType
{
	type T <: IClass
}

trait MClass
  extends ZClass
  with MType
{
	type T <: MClass
}
