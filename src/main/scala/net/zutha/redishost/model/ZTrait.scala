package net.zutha.redishost.model

object ZTrait extends ItemFactory[ZTrait, ITrait, MTrait] {

  def name = "ZTrait"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZTrait
  extends ZObjectType
{
	type T <: ZTrait
}

trait ITrait
  extends ZTrait
  with IObjectType
{
	type T <: ITrait
}

trait MTrait
  extends ZTrait
  with MObjectType
{
	type T <: MTrait
}
