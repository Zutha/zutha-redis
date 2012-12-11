package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model._

trait RoleFieldMember extends ZFieldMember

case class IRoleFieldMember( role: IRef[ZRole], players: Seq[IRef[ZObject]] )
  extends RoleFieldMember
  with IFieldMember

case class MRoleFieldMember( role: MRef[ZRole], players: Seq[MRef[ZObject]] )
  extends RoleFieldMember
  with MFieldMember
