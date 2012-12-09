package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZDatatype extends ZItemClassCompanion[ZDatatype, IDatatype, MDatatype] {

  def name = "Datatype"

}

trait ZDatatype
  extends ZType
  with ZItemLike[ZDatatype]

trait IDatatype
  extends ZDatatype
  with IType
  with IItemLike[IDatatype]

trait MDatatype
  extends ZDatatype
  with MType
  with MItemLike[MDatatype]
