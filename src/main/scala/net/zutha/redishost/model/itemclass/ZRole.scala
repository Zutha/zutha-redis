package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton._
import fieldmember._
import net.zutha.redishost.model.ZRef

object ZRole
  extends ZSingleton[ZItemClass with ZRole with ZTrait]
  with ZItemClassSingleton[ZRole]
  with ZRoleSingleton
  with ZTraitSingleton[ZRole]
{

  def name = "Role"
}

trait ZRole
  extends ZFieldMemberType
  with Referenceable[ZRole]
{
  def makeRolePlayer( player: ZRef[A, ZObject] )( implicit acc: A ): RolePlayer[A] = RolePlayer[A]( this.zRef, player )
  def -> ( player: ZRef[A, ZObject] )( implicit acc: A ): RolePlayer[A] = makeRolePlayer( player )
}