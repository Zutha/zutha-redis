package net.zutha.redishost.model

object ZFieldClass extends ItemFactory[ZFieldClass, IFieldClass, MFieldClass] {

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
