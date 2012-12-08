package net.zutha.redishost.model.literal

import net.zutha.redishost.model.companion.ZLiteralTypeCompanion

object FieldCardMax extends ZLiteralTypeCompanion {

  def name = "Field Card Max"
}
case class FieldCardMax( value: Int ) extends LiteralValue
