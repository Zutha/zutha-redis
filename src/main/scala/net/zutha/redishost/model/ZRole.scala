package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZRole extends ZObjectFactory[ZRole, IRole, MRole] {
  def typeName = "ZRole"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZRole
  extends ZType
{
	type T <: ZRole

}

trait IRole
  extends ZRole
  with IType
{
	type T <: IRole
}

trait MRole
  extends ZRole
  with MType
{
	type T <: MRole
}
