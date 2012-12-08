package net.zutha.redishost.model.datatype

import net.zutha.redishost.model.companion.ZDatatypeCompanion
import net.zutha.redishost.literal.{LiteralValueLike, LiteralValue}

object UnboundedNonNegativeInteger extends ZDatatypeCompanion[UnboundedNonNegativeInteger] {

  def name = "Unbounded Non-Negative Integer"

  def make( value: String ): Option[UnboundedNonNegativeInteger] = value match {
    case "*" => Some(Infinity)
    case v => try {
        Some(Finite(v.toInt))
      } catch {
        case e: Exception => None
      }
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
      case Finite(otherVal) => 1
    }
  }

  case class Finite(value: Int) extends UnboundedNonNegativeInteger {
    require( value >= 0, "UnboundedNonNegativeInteger literals cannot be negative." )
    def asString = value.toString

    def compare(other: UnboundedNonNegativeInteger) = other match {
      case Infinity => -1
      case Finite(otherVal) => (value - otherVal).signum
    }
  }

  // implicit conversions
  import scala.language.implicitConversions
  implicit def intToUnboundedNNI( value:Int ):UnboundedNonNegativeInteger = Finite(value)
}

trait UnboundedNonNegativeInteger 
  extends LiteralValue
  with LiteralValueLike[UnboundedNonNegativeInteger]
  with Ordered[UnboundedNonNegativeInteger]
{
  protected def datatypeFactory = UnboundedNonNegativeInteger

}