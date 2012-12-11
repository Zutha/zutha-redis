package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZDatatype extends ZItemClassSingleton[ZDatatype] {

  def name = "Datatype"

}

trait ZDatatype
  extends ZType
  with ZItemLike[ZDatatype]
