package net.zutha.redishost.model

object ZObjectType extends ZItemClassCompanion[ZObjectType, IObjectType, MObjectType] {

  def name = "ZObjectType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZObjectType
  extends ZType
{
	type T <: ZObjectType
}

trait IObjectType
  extends ZObjectType
  with IType
{
	type T <: IObjectType
}

trait MObjectType
  extends ZObjectType
  with MType
{
	type T <: MObjectType
}
