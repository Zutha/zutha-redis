package net.zutha.redishost.model.datatype

import net.zutha.redishost.model.singleton.ZDatatypeSingleton
import net.zutha.redishost.model.fieldmember.{LiteralValueLike, LiteralValue}

object UnboundedNonNegativeInteger extends ZDatatypeSingleton[UnboundedNonNegativeInteger] {

  def name = "Unbounded Non-Negative Integer"

  def make( value: String ): Option[UnboundedNonNegativeInteger] = value match {
    case "*" => Some(Infinity)
    case v => NonNegativeInteger.make( v )
  }

  def unapply[T >: UnboundedNonNegativeInteger <: LiteralValue]( value: T ): Option[String] = value match {
    case v: UnboundedNonNegativeInteger => Some( v.asString )
    case _ => None
  }

  // data constructors
  def * = Infinity
  object Infinity extends UnboundedNonNegativeInteger {
    def asString = "*"
    def compare(other: UnboundedNonNegativeInteger) = other match {
      case Infinity => 0
      case v: NonNegativeInteger => 1
    }
  }

}

trait UnboundedNonNegativeInteger 
  extends LiteralValue
  with LiteralValueLike[UnboundedNonNegativeInteger]
  with Ordered[UnboundedNonNegativeInteger]
{
  protected def datatypeFactory = UnboundedNonNegativeInteger

}