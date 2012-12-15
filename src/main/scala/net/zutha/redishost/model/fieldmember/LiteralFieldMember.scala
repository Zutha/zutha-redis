package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.db.Accessor
import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._

object LiteralFieldMember {
  import scala.language.implicitConversions
  implicit def pairToLiteral[A <: Accessor[A]]( pair: Pair[ZRef[A, ZLiteralType], LiteralValue]
                                                ): LiteralFieldMember[A] = LiteralFieldMember( pair._1, pair._2 )

}

case class LiteralFieldMember[A <: Accessor[A]]( literalType: ZRef[A, ZLiteralType],
                                                 value: LiteralValue )
  extends FieldMember[A]
{
  def toPair: Pair[ZRef[A, ZLiteralType], LiteralValue] = Pair( literalType, value )
}