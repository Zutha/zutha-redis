package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model.fieldmember._
import net.zutha.redishost.model.{ZItemLike, MItemLike, IItemLike}

object ZLiteralType extends ZItemClassSingleton[ZLiteralType, ILiteralType, MLiteralType] {

  def name = "LiteralType"
}

trait ZLiteralType
  extends ZFieldMemberType
  with ZItemLike[ZLiteralType]
{

  def datatype: ZDatatype

}

trait ILiteralType
  extends ZLiteralType
  with IFieldMemberType
  with IItemLike[ILiteralType]
{

  def datatype: IDatatype = ???

  def makeLiteral( value: LiteralValue )( implicit acc: ImmutableAccessor ): ILiteral = ILiteral( this.ref, value )
  def -> ( value: LiteralValue )( implicit acc: ImmutableAccessor ): ILiteral = makeLiteral( value )
}

trait MLiteralType
  extends ZLiteralType
  with MFieldMemberType
  with MItemLike[MLiteralType]
{

  def datatype: MDatatype = ???

  def makeLiteral( value: LiteralValue )( implicit acc: MutableAccessor ): MLiteral = MLiteral( this.ref, value )
  def -> ( value: LiteralValue )( implicit acc: MutableAccessor ): MLiteral = makeLiteral( value )
}
