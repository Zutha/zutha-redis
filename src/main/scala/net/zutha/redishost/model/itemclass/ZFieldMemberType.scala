package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZFieldMemberType
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZFieldMemberType]
  with ZRoleSingleton
{

  def name = "FieldMemberType"
}

trait ZFieldMemberType
  extends ZType
  with Referenceable[ZFieldMemberType]
