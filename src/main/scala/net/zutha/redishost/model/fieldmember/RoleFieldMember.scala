package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass.{IObject, IRole, MObject, MRole}

trait RoleFieldMember extends ZFieldMember

case class IRoleFieldMember( role: IRef[IRole], players: Seq[IRef[IObject]] )
  extends RoleFieldMember
  with IFieldMember

case class MRoleFieldMember( role: MRef[MRole], players: Seq[MRef[MObject]] )
  extends RoleFieldMember
  with MFieldMember
