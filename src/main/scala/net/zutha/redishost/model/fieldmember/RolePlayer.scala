package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.Accessor

object RolePlayer {
  import scala.language.implicitConversions
  implicit def rolePlayerToFieldMember[A <: Accessor[A]]( rolePlayer: RolePlayer[A] ): FieldMember[A] =
    RoleFieldMember( rolePlayer.role, Seq(rolePlayer.player) )

  implicit def pairToRolePlayer[A <: Accessor[A]]( pair: Pair[ZRef[A, ZRole], ZRef[A, ZObject]]
                                                   ): RolePlayer[A] = RolePlayer( pair._1, pair._2 )
}


case class RolePlayer[A <: Accessor[A]]( role: ZRef[A, ZRole],
                                         player: ZRef[A, ZObject] )
{
  def toPair = Pair( role, player )
}
