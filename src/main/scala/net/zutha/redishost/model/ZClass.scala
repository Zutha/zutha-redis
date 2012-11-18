package net.zutha.redishost.model

object ZClass extends ItemFactory[ZClass, IClass, MClass] {

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
