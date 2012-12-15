package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import fieldmember.RolePlayer
import itemclass._
import net.zutha.redishost.db.Accessor

protected[redishost] trait ZRoleSingleton
  extends ZTypeSingleton
  with ZSingletonLike[ZRole]
{
  self: ZSingleton[ZRole] =>

  def makeRolePlayer[A <: Accessor[A]]( player: ZRef[A, ZObject] )
                                      ( implicit acc: A ): RolePlayer[A] =
    RolePlayer[A]( this.ref[A, ZRole], player )

  def -> [A <: Accessor[A]]( player: ZRef[A, ZObject] )
                           ( implicit acc: A ): RolePlayer[A] =
    makeRolePlayer( player )
}
