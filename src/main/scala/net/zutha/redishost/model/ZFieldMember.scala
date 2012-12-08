package net.zutha.redishost.model

import itemclass._

object ZFieldMember {
  import scala.language.implicitConversions
  implicit def rolePlayerToFieldMember( rolePlayer: IRolePlayer ): IFieldMember = rolePlayer match {
    case (role, player) => IRoleFieldMember( role, Seq(player) )
  }
  implicit def rolePlayerToFieldMember( rolePlayer: MRolePlayer ): MFieldMember = rolePlayer match {
    case (role, player) => MRoleFieldMember( role, Seq(player) )
  }

}

trait ZFieldMember

trait IFieldMember extends ZFieldMember

case class IRoleFieldMember(role: IRef[IRole], players: Seq[IRef[IObject]])
  extends IFieldMember


trait MFieldMember extends ZFieldMember

case class MRoleFieldMember(role: MRef[MRole], players: Seq[MRef[MObject]])
  extends MFieldMember


