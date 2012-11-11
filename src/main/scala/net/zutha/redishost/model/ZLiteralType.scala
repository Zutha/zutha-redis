package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZLiteralType extends ZObjectFactory[ZLiteralType, ILiteralType, MLiteralType] {
  def typeName = "ZLiteralType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZLiteralType
  extends ZFieldMemberType
{
	type T <: ZLiteralType
}

trait ILiteralType
  extends ZLiteralType
  with IFieldMemberType
{
	type T <: ILiteralType
}

trait MLiteralType
  extends ZLiteralType
  with MFieldMemberType
{
	type T <: MLiteralType
}
