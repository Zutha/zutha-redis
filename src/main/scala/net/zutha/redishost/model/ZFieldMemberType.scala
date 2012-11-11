package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZFieldMemberType extends ZObjectFactory[ZFieldMemberType, IFieldMemberType, MFieldMemberType] {
  def typeName = "ZFieldMemberType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZFieldMemberType
  extends ZType
{
	type T <: ZFieldMemberType
}

trait IFieldMemberType
  extends ZFieldMemberType
  with IType
{
	type T <: IFieldMemberType
}

trait MFieldMemberType
  extends ZFieldMemberType
  with MType
{
	type T <: MFieldMemberType
}
