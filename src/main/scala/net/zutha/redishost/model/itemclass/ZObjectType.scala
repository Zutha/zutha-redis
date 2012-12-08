package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZObjectType extends ZItemClassCompanion[ZObjectType, IObjectType, MObjectType] {

  def name = "ObjectType"


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
