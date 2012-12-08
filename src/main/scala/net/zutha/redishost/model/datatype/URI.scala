package net.zutha.redishost.model.datatype

import net.zutha.redishost.model.companion.ZDatatypeCompanion
import net.zutha.redishost.model.fieldmember.{LiteralValueLike, LiteralValue}

object URI extends ZDatatypeCompanion[URI] {

  def name = "URI"

  def make( value: String ): Option[URI] = try {
    Some( apply( java.net.URI.create(value) ) )
  } catch {
    case e: IllegalArgumentException => None
  }

  def unapply[T >: URI <: LiteralValue]( value: T ): Option[String] = value match {
    case v: URI => Some( v.asString )
    case _ => None
  }
}

case class URI( value: java.net.URI )
  extends LiteralValue
  with LiteralValueLike[URI]
{
  protected def datatypeFactory = URI

  def asString = value.toString
}
