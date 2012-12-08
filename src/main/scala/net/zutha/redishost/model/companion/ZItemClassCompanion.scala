package net.zutha.redishost.model.companion

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZItemClassCompanion
[
  T <: ZItem,
  TI <: T with IItem,
  TM <: T with MItem
]
  extends ZClassCompanion[T, TI, TM]
{
  type ObjC = ZItemClass
  type ObjCI = IItemClass
  type ObjCM = MItemClass

  def classFactory = ZItemClass
}
