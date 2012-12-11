package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._

object Literal {
  import scala.language.implicitConversions
  implicit def pairToLiteral( pair: Pair[IRef[ZLiteralType], LiteralValue] ): ILiteral = ILiteral( pair._1, pair._2 )
  implicit def pairToLiteral( pair: Pair[MRef[ZLiteralType], LiteralValue] ): MLiteral = MLiteral( pair._1, pair._2 )
}

trait Literal extends ZFieldMember
{
  def literalType: Ref[ZLiteralType]
  def value: LiteralValue
  def toPair: Pair[Ref[ZLiteralType], LiteralValue]
}

case class ILiteral( literalType: IRef[ZLiteralType], value: LiteralValue)
  extends Literal
  with IFieldMember
{
  implicit def acc: ImmutableAccessor = literalType.acc
  require( value.datatypeI == literalType.load.datatype.ref )
  def toPair = Pair(literalType, value)
}


case class MLiteral( literalType: MRef[ZLiteralType], value: LiteralValue)
  extends Literal
  with MFieldMember
{
  implicit def acc: MutableAccessor = literalType.acc
  require( value.datatypeM == literalType.load.datatype.ref )
  def toPair = Pair(literalType, value)
}
