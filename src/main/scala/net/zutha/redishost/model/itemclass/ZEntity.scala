package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZEntity extends ZItemClassSingleton[ZEntity, IEntity, MEntity] {

  def name = "Entity"


}

trait ZEntity
  extends ZItem
  with ZItemLike[ZEntity]

trait IEntity
  extends IItem
  with ZEntity
  with IItemLike[IEntity]

trait MEntity
  extends ZEntity
  with MItem
  with MItemLike[MEntity]
