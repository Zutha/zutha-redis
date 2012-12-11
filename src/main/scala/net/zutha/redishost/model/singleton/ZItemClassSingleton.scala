package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZItemClassSingleton[T <: ZItem]
  extends ZClassSingleton[T]
  with ZObjectTypeSingleton[ZI, T]
  with ZItemSingleton[ZItemClass]
{

}
