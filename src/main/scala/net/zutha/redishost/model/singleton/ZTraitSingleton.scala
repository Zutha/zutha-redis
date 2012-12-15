package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._
import special.ZNothing

protected[redishost] trait ZTraitSingleton[+T >: ZNothing <: ZObject]
  extends ZObjectTypeSingleton[T]
  with ZSingletonLike[ZTrait]
{
  self: ZSingleton[ZTrait] =>
}
