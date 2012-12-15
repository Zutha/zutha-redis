package net.zutha.redishost.model.`trait`

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.singleton.{ZSingleton, ZTraitSingleton, ZRoleSingleton}
import net.zutha.redishost.model.Referenceable

object ZFieldDeclarer
  extends ZSingleton[ZTrait with ZRole]
  with ZTraitSingleton[ZFieldDeclarer]
  with ZRoleSingleton
{

  def name = "Field Declarer"
}

trait ZFieldDeclarer
  extends ZTrait
  with Referenceable[ZFieldDeclarer]
