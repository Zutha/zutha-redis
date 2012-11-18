package net.zutha.redishost.model

object ZType extends ZItemClassCompanion[ZType, IType, MType] {

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
