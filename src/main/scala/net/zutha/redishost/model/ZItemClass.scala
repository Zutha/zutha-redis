package net.zutha.redishost.model

object ZItemClass extends ZItemClassCompanion[ZItemClass, IItemClass, MItemClass] {

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
