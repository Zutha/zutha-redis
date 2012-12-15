package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model._
import itemclass.{ZObject, ZRole}
import net.zutha.redishost.db.Accessor

case class RoleFieldMember[A <: Accessor[A]]( role: ZRef[A, ZRole], players: Seq[ZRef[A, ZObject]] )
  extends FieldMember[A]
