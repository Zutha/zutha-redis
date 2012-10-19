package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZLiteralType extends ZObjectFactory[ZLiteralType, ILiteralType, MLiteralType] {
  def typeName = "ZLiteralType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZLiteralType
  extends ZType
{
	type T <: ZLiteralType
}

trait ILiteralType[A <: ImmutableAccessor]
  extends ZLiteralType
  with IType[A]
{
	type T <: ILiteralType[A]
}

trait MLiteralType[A <: MutableAccessor]
  extends ZLiteralType
  with MType[A]
{
	type T <: MLiteralType[A]
}
