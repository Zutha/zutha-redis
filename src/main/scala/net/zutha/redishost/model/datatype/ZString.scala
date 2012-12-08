package net.zutha.redishost.model.datatype

import net.zutha.redishost.model.companion.ZDatatypeCompanion
import net.zutha.redishost.model.fieldmember.{LiteralValueLike, LiteralValue}

object ZString extends ZDatatypeCompanion[ZString] {

  def name = "String"

  def make( value: String ): Option[ZString] = Some(apply(value))

  def unapply[T >: ZString <: LiteralValue]( value: T ): Option[String] = value match {
    case v: ZString => Some( v.asString )
    case _ => None
  }

  // implicit conversions
  import scala.language.implicitConversions
  implicit def stringToZString( value: String ): ZString = ZString( value )
}

case class ZString( value: String )
  extends LiteralValue
  with LiteralValueLike[ZString]
  with Ordered[ZString]
{
  protected def datatypeFactory = ZString

  def compare(that: ZString) = this.value compareTo that.value

  def asString = value
}
