package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZDatatype
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZDatatype]
  with ZRoleSingleton
{

  def name = "Datatype"

}

trait ZDatatype
  extends ZType
  with Referenceable[ZDatatype]
