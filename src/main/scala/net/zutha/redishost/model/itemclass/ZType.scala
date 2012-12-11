package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZType extends ZItemClassSingleton[ZType, IType, MType] {

  def name = "Type"


}

trait ZType
  extends ZItem
  with ZItemLike[ZType]

trait IType
  extends ZType
  with IItem
  with IItemLike[IType]

trait MType
  extends ZType
  with MItem
  with MItemLike[MType]
