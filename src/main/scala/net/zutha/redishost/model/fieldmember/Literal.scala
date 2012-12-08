package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._

trait Literal extends ZFieldMember
{
  def literalType: ZRef[ZLiteralType]
  def value: LiteralValue
}


case class ILiteral( literalType: IRef[ILiteralType], value: LiteralValue)
  extends Literal
  with IFieldMember
{
  implicit def acc: ImmutableAccessor = literalType.acc
  require( value.datatypeI == literalType.get.datatype.ref )
}


case class MLiteral( literalType: MRef[MLiteralType], value: LiteralValue)
  extends Literal
  with MFieldMember
{
  implicit def acc: MutableAccessor = literalType.acc
  require( value.datatypeM == literalType.get.datatype.ref )
}
