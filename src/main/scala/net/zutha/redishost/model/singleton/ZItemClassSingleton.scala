package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZItemClassSingleton
[
  T <: ZItem,
  TI <: T with IItem,
  TM <: T with MItem
]
  extends ZClassSingleton[T, TI, TM]
  with ZItemSingleton[ZItemClass, IItemClass, MItemClass]
{

}
