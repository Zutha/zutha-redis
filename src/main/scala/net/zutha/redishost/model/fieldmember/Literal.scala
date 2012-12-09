package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._

object Literal {
  import scala.language.implicitConversions
  implicit def pairToLiteral( pair: Pair[IRef[ILiteralType], LiteralValue] ): ILiteral = ILiteral( pair._1, pair._2 )
  implicit def pairToLiteral( pair: Pair[MRef[MLiteralType], LiteralValue] ): MLiteral = MLiteral( pair._1, pair._2 )
}

trait Literal extends ZFieldMember
{
  def literalType: ZRef[ZLiteralType]
  def value: LiteralValue
  def toPair: Pair[ZRef[ZLiteralType], LiteralValue]
}

case class ILiteral( literalType: IRef[ILiteralType], value: LiteralValue)
  extends Literal
  with IFieldMember
{
  implicit def acc: ImmutableAccessor = literalType.acc
  require( value.datatypeI == literalType.get.datatype.ref )
  def toPair = Pair(literalType, value)
}


case class MLiteral( literalType: MRef[MLiteralType], value: LiteralValue)
  extends Literal
  with MFieldMember
{
  implicit def acc: MutableAccessor = literalType.acc
  require( value.datatypeM == literalType.get.datatype.ref )
  def toPair = Pair(literalType, value)
}
