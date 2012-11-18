package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZItemClass extends ObjectFactory[ZItemClass, IItemClass, MItemClass] {
  type ObjT = ZItemClass
  type ObjTM = MItemClass
  type ObjTI = IItemClass

  def name = "ZItemClass"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZItemClass
  extends ZClass
{
	type T <: ZItemClass
}

trait IItemClass
  extends ZItemClass
  with IClass
{
	type T <: IItemClass
}

trait MItemClass
  extends ZItemClass
  with MClass
{
	type T <: MItemClass
}
