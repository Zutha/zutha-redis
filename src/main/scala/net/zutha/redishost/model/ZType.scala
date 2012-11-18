package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZType extends ObjectFactory[ZType, IType, MType] {
  type ObjT = ZItemClass
  type ObjTM = MItemClass
  type ObjTI = IItemClass

  def name = "ZType"

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
