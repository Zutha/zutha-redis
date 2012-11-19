package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZClass extends ZItemClassCompanion[ZClass, IClass, MClass] {

  def name = "Class"

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
