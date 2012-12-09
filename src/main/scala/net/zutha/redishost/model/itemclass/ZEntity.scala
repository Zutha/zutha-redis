package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZEntity extends ZItemClassCompanion[ZEntity, IEntity, MEntity] {

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
