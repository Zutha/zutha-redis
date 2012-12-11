package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._

protected[redishost] trait ZRoleSingleton
  extends ZTypeSingleton
  with ZItemSingleton[ZRole, IRole, MRole]
{

}
