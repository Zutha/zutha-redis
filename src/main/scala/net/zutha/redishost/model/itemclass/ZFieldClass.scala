package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZFieldClass
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZFieldClass]
  with ZRoleSingleton
{

  def name = "FieldClass"

  type ObjT = ZItemClass with ZRole
}

trait ZFieldClass
  extends ZClass
  with Referenceable[ZFieldClass]
