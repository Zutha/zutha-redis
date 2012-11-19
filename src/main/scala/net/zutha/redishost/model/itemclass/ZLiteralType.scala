package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZLiteralType extends ZItemClassCompanion[ZLiteralType, ILiteralType, MLiteralType] {

  def name = "LiteralType"

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
