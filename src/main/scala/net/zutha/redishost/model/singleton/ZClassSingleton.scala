package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZClassSingleton[+T <: ZObject]
  extends ZObjectTypeSingleton[ZObjT, T]
  with ZItemSingleton[ZClass]
{

}
