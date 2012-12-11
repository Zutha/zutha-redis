package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._

object RolePlayer {
  import scala.language.implicitConversions
  implicit def rolePlayerToFieldMember( rolePlayer: IRolePlayer ): IFieldMember = rolePlayer match {
    case IRolePlayer(role, player) => IRoleFieldMember( role, Seq(player) )
  }
  implicit def rolePlayerToFieldMember( rolePlayer: MRolePlayer ): MFieldMember = rolePlayer match {
    case MRolePlayer(role, player) => MRoleFieldMember( role, Seq(player) )
  }

  implicit def pairToRolePlayer( pair: Pair[IRef[ZRole], IRef[ZObject]] ): IRolePlayer = IRolePlayer( pair._1, pair._2 )
  implicit def pairToRolePlayer( pair: Pair[MRef[ZRole], MRef[ZObject]] ): MRolePlayer = MRolePlayer( pair._1, pair._2 )
}

trait RolePlayer {
  def role: Ref[ZRole]
  def player: Ref[ZObject]
  def toPair: Pair[Ref[ZRole], Ref[ZObject]]
}

case class IRolePlayer( role: IRef[ZRole], player: IRef[ZObject] )
  extends RolePlayer
{
  def toPair = Pair( role, player )
}

case class MRolePlayer( role: MRef[ZRole], player: MRef[ZObject] )
  extends RolePlayer
{
  def toPair = Pair( role, player )
}