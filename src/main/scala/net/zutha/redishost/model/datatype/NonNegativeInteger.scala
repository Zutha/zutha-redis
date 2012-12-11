package net.zutha.redishost.model.datatype

import net.zutha.redishost.model.singleton.ZDatatypeSingleton
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger._

object NonNegativeInteger extends ZDatatypeSingleton[NonNegativeInteger] {

  def name = "Non-Negative Integer"

  def make( value: String ): Option[NonNegativeInteger] = try {
      Some( NonNegativeInteger( value.toInt ) )
    } catch {
      case e: NumberFormatException => None
      case e: IllegalArgumentException => None
    }

  // implicit conversions
  import scala.language.implicitConversions
  implicit def intToUnboundedNNI( value:Int ):UnboundedNonNegativeInteger = NonNegativeInteger(value)
}


case class NonNegativeInteger( value: Int ) extends UnboundedNonNegativeInteger {
  require( value >= 0, "NonNegativeInteger literals cannot be negative." )
  def asString = value.toString

  def compare(other: UnboundedNonNegativeInteger) = other match {
    case Infinity => -1
    case NonNegativeInteger( otherVal ) => (value - otherVal).signum
  }
}