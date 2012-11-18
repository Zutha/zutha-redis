package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZEntity extends ObjectFactory[ZEntity, IEntity, MEntity] {
  type ObjT = ZItemClass
  type ObjTM = MItemClass
  type ObjTI = IItemClass

  def name = "ZEntity"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZEntity
  extends ZItem
{
	type T <: ZEntity
}

trait IEntity
  extends IItem
  with ZEntity
{
	type T <: IEntity
}

trait MEntity
  extends ZEntity
  with MItem
{
	type T <: MEntity
}
