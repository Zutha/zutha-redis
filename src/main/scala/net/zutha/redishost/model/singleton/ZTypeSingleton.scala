package net.zutha.redishost.model.singleton

import net.zutha.redishost.model.itemclass._

protected[redishost] trait ZTypeSingleton
  extends ZSingletonLike[ZType]
{
  self: ZSingleton[ZType] =>


}
