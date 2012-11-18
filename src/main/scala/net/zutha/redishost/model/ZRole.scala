package net.zutha.redishost.model

object ZRole extends ZItemClassCompanion[ZRole, IRole, MRole] {

  type ObjT = ZItemClass with ZRole with ZTrait
  type ObjTI = IItemClass with IRole with ITrait
  type ObjTM = MItemClass with MRole with MTrait

  def name = "ZRole"

  def validType_?(obj: ZObject): Boolean = ???

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
