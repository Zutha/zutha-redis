package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZFieldClass extends ZItemClassCompanion[ZFieldClass, IFieldClass, MFieldClass] {

  def name = "FieldClass"

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
