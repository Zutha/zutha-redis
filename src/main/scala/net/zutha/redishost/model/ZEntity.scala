package net.zutha.redishost.model

object ZEntity extends ItemFactory[ZEntity, IEntity, MEntity] {

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
