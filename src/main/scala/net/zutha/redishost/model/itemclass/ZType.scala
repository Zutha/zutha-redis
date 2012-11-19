package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZType extends ZItemClassCompanion[ZType, IType, MType] {

  def name = "Type"

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
