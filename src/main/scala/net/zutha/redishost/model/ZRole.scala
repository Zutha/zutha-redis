package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZRole extends ObjectFactory[ZRole, IRole, MRole] {
  def name = "ZRole"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZRole
  extends ZFieldMemberType
{
	type T <: ZRole
}

trait IRole
  extends ZRole
  with IFieldMemberType
{
	type T <: IRole
}

trait MRole
  extends ZRole
  with MFieldMemberType
{
	type T <: MRole
}
