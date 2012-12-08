package net.zutha.redishost.model.literal

import net.zutha.redishost.model.companion.ZLiteralTypeCompanion

object FieldCardMin extends ZLiteralTypeCompanion {

  def name = "Field Card Min"
}
case class FieldCardMin( value: Int ) extends LiteralValue
