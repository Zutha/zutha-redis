package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import fieldclass._
import itemclass._
import special.ZNothing

protected[redishost] trait ZFieldClassSingleton[+T >: ZNothing <: ZField]
  extends ZClassSingleton[T]
  with ZSingletonLike[ZFieldClass]
{
  self: ZSingleton[ZFieldClass] =>
}
