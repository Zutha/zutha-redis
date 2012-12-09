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

  implicit def pairToRolePlayer( pair: Pair[IRef[IRole], IRef[IObject]] ): IRolePlayer = IRolePlayer( pair._1, pair._2 )
  implicit def pairToRolePlayer( pair: Pair[MRef[MRole], MRef[MObject]] ): MRolePlayer = MRolePlayer( pair._1, pair._2 )
}

trait RolePlayer {
  def role: ZRef[ZRole]
  def player: ZRef[ZObject]
  def toPair: Pair[ZRef[ZRole], ZRef[ZObject]]
}

case class IRolePlayer( role: IRef[IRole], player: IRef[IObject] )
  extends RolePlayer
{
  def toPair = Pair( role, player )
}

case class MRolePlayer( role: MRef[MRole], player: MRef[MObject] )
  extends RolePlayer
{
  def toPair = Pair( role, player )
}