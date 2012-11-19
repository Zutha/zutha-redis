package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZItemClass extends ZItemClassCompanion[ZItemClass, IItemClass, MItemClass] {

  def name = "ItemClass"

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
