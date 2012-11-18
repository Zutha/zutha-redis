package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZClass extends ObjectFactory[ZClass, IClass, MClass] {
  type ObjT = ZItemClass
  type ObjTM = MItemClass
  type ObjTI = IItemClass

  def name = "ZClass"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZClass
  extends ZObjectType
{
	type T <: ZClass
}

trait IClass
  extends ZClass
  with IObjectType
{
	type T <: IClass
}

trait MClass
  extends ZClass
  with MObjectType
{
	type T <: MClass
}
