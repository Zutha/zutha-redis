package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZItemSingleton, ZClassSingleton}

object ZObject
  extends ZClassSingleton[ZObject, IObject, MObject]
  with ZItemSingleton[
    ZClass with ZRole,
    IClass with IRole,
    MClass with MRole ]
{

  def name = "Object"

}

trait ZObject extends ZObjectLike[ZObject]
