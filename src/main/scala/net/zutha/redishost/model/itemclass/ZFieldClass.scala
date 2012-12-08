package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZFieldClass extends ZItemClassCompanion[ZFieldClass, IFieldClass, MFieldClass] {

  def name = "FieldClass"

  type ObjT = ZItemClass with ZRole
  type ObjTI = IItemClass with IRole
  type ObjTM = MItemClass with MRole
}

trait ZFieldClass
  extends ZClass
{
	type T <: ZFieldClass
}

trait IFieldClass
  extends ZFieldClass
  with IClass
{
	type T <: IFieldClass
}

trait MFieldClass
  extends ZFieldClass
  with MClass
{
	type T <: MFieldClass
}
