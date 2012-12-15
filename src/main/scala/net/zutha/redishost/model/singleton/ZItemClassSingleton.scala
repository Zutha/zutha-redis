package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._
import special.ZNothing

protected[redishost] trait ZItemClassSingleton[+T >: ZNothing <: ZItem]
  extends ZClassSingleton[T]
  with ZSingletonLike[ZItemClass]
{
  self: ZSingleton[ZItemClass] =>
}
