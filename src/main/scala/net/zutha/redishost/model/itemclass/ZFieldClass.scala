package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZFieldClass extends ZItemClassCompanion[ZFieldClass, IFieldClass, MFieldClass] {

  def name = "FieldClass"

  type ObjT = ZItemClass with ZRole
  type ObjTI = IItemClass with IRole
  type ObjTM = MItemClass with MRole
}

trait ZFieldClass
  extends ZClass
  with ZItemLike[ZFieldClass]

trait IFieldClass
  extends ZFieldClass
  with IClass
  with IItemLike[IFieldClass]

trait MFieldClass
  extends ZFieldClass
  with MClass
  with MItemLike[MFieldClass]
