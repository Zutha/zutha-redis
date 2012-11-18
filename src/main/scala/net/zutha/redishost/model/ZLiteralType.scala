package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZLiteralType extends ObjectFactory[ZLiteralType, ILiteralType, MLiteralType] {
  def name = "ZLiteralType"

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
