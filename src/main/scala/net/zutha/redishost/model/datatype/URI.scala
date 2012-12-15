package net.zutha.redishost.model.datatype

import net.zutha.redishost.model.singleton.{ZSingleton, ZDatatypeSingleton}
import net.zutha.redishost.model.fieldmember.{LiteralValueLike, LiteralValue}
import net.zutha.redishost.model.itemclass.ZDatatype

object URI
  extends ZSingleton[ZDatatype]
  with ZDatatypeSingleton[URI]
{

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
