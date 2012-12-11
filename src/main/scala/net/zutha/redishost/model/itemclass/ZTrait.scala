package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZTrait extends ZItemClassSingleton[ZTrait, ITrait, MTrait] {

  def name = "Trait"


}

trait ZTrait
  extends ZObjectType
  with ZItemLike[ZTrait]

trait ITrait
  extends ZTrait
  with IObjectType
  with IItemLike[ITrait]

trait MTrait
  extends ZTrait
  with MObjectType
  with MItemLike[MTrait]
