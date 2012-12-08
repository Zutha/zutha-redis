package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZTrait extends ZItemClassCompanion[ZTrait, ITrait, MTrait] {

  def name = "Trait"


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
