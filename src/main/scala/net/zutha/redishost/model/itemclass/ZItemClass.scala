package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZItemClass extends ZItemClassSingleton[ZItemClass, IItemClass, MItemClass] {

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
