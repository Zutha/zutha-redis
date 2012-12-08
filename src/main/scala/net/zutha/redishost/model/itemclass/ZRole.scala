package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model.{MRef, IRef}
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.fieldmember.{MRolePlayer, IRolePlayer}

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

  def makeRolePlayer( player: IRef[IObject] )( implicit acc: ImmutableAccessor ): IRolePlayer = IRolePlayer( this.ref, player )
  def -> ( player: IRef[IObject] )( implicit acc: ImmutableAccessor ): IRolePlayer = makeRolePlayer( player )
}

trait MRole
  extends ZRole
  with MFieldMemberType
{
	type T <: MRole

  def makeRolePlayer( player: MRef[MObject] )( implicit acc: MutableAccessor ): MRolePlayer = MRolePlayer( this.ref, player )
  def -> ( player: MRef[MObject] )( implicit acc: MutableAccessor ): MRolePlayer = makeRolePlayer( player )
}
