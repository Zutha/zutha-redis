package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZClass extends ZItemClassCompanion[ZClass, IClass, MClass] {

  def name = "Class"


}

trait ZClass
  extends ZObjectType
  with ZItemLike[ZClass]

trait IClass
  extends ZClass
  with IObjectType
  with IItemLike[IClass]

trait MClass
  extends ZClass
  with MObjectType
  with MItemLike[MClass]
