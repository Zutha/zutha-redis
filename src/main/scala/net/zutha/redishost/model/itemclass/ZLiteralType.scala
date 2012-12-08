package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.literal.{LiteralValue, MLiteral, ILiteral}

object ZLiteralType extends ZItemClassCompanion[ZLiteralType, ILiteralType, MLiteralType] {

  def name = "LiteralType"
}

trait ZLiteralType
  extends ZFieldMemberType
{
	type T <: ZLiteralType

  def datatype: ZDatatype

}

trait ILiteralType
  extends ZLiteralType
  with IFieldMemberType
{
	type T <: ILiteralType

  def datatype: IDatatype = ???

  def makeLiteral( value: LiteralValue )( implicit acc: ImmutableAccessor ): ILiteral = ILiteral( this.ref, value )
  def -> ( value: LiteralValue )( implicit acc: ImmutableAccessor ): ILiteral = makeLiteral( value )
}

trait MLiteralType
  extends ZLiteralType
  with MFieldMemberType
{
	type T <: MLiteralType

  def datatype: MDatatype = ???

  def makeLiteral( value: LiteralValue )( implicit acc: MutableAccessor ): MLiteral = MLiteral( this.ref, value )
  def -> ( value: LiteralValue )( implicit acc: MutableAccessor ): MLiteral = makeLiteral( value )
}
