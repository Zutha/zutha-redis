package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZFieldClass extends ObjectFactory[ZFieldClass, IFieldClass, MFieldClass] {
  type ObjT = ZItemClass
  type ObjTM = MItemClass
  type ObjTI = IItemClass

  def name = "ZFieldClass"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZFieldClass
  extends ZClass
{
	type T <: ZFieldClass
}

trait IFieldClass
  extends ZFieldClass
  with IClass
{
	type T <: IFieldClass
}

trait MFieldClass
  extends ZFieldClass
  with MClass
{
	type T <: MFieldClass
}
