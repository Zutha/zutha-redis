package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.fieldmember.{MRolePlayer, IRolePlayer}

object ZRole extends ZItemClassSingleton[ZRole, IRole, MRole] {

  type ObjT = ZItemClass with ZRole with ZTrait
  type ObjTI = IItemClass with IRole with ITrait
  type ObjTM = MItemClass with MRole with MTrait

  def name = "Role"
}

trait ZRole
  extends ZFieldMemberType
  with ZItemLike[ZRole]

trait IRole
  extends ZRole
  with IFieldMemberType
  with IItemLike[IRole]
{
  def makeRolePlayer( player: IRef[ZObject] )( implicit acc: ImmutableAccessor ): IRolePlayer = IRolePlayer( this.ref, player )
  def -> ( player: IRef[ZObject] )( implicit acc: ImmutableAccessor ): IRolePlayer = makeRolePlayer( player )
}

trait MRole
  extends ZRole
  with MFieldMemberType
  with MItemLike[MRole]
{
  def makeRolePlayer( player: MRef[ZObject] )( implicit acc: MutableAccessor ): MRolePlayer = MRolePlayer( this.ref, player )
  def -> ( player: MRef[ZObject] )( implicit acc: MutableAccessor ): MRolePlayer = makeRolePlayer( player )
}
