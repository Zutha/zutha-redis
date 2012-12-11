package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZClassSingleton
[
  T <: ZObject,
  TI <: T with IObject,
  TM <: T with MObject
]
  extends ZObjectTypeSingleton[T, TI, TM]
  with ZItemSingleton[ZClass, IClass, MClass]
{

}
