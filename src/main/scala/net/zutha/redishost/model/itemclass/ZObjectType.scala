package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZObjectType extends ZItemClassSingleton[ZObjectType, IObjectType, MObjectType] {

  def name = "ObjectType"

}

trait ZObjectType
  extends ZType
  with ZItemLike[ZObjectType]

trait IObjectType
  extends ZObjectType
  with IType
  with IItemLike[IObjectType]

trait MObjectType
  extends ZObjectType
  with MType
  with MItemLike[MObjectType]
