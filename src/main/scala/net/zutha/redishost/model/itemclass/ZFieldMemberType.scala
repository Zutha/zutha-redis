package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZFieldMemberType extends ZItemClassCompanion[ZFieldMemberType, IFieldMemberType, MFieldMemberType] {

  def name = "FieldMemberType"

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
