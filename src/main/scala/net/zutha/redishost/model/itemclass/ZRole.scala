package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZRole extends ZItemClassCompanion[ZRole, IRole, MRole] {

  type ObjT = ZItemClass with ZRole with ZTrait
  type ObjTI = IItemClass with IRole with ITrait
  type ObjTM = MItemClass with MRole with MTrait

  def name = "Role"
}

trait ZRole
  extends ZFieldMemberType
{
	type T <: ZRole
}

trait IRole
  extends ZRole
  with IFieldMemberType
{
	type T <: IRole
}

trait MRole
  extends ZRole
  with MFieldMemberType
{
	type T <: MRole
}
