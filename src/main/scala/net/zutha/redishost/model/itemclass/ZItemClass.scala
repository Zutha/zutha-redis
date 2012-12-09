package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZItemClass extends ZItemClassCompanion[ZItemClass, IItemClass, MItemClass] {

  def name = "ItemClass"

}

trait ZItemClass
  extends ZClass
  with ZItemLike[ZItemClass]

trait IItemClass
  extends ZItemClass
  with IClass
  with IItemLike[IItemClass]

trait MItemClass
  extends ZItemClass
  with MClass
  with MItemLike[MItemClass]
