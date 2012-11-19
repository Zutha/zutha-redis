package net.zutha.redishost.model.literal

import net.zutha.redishost.model._
import companion.ZLiteralTypeCompanion
import itemclass.ZLiteralType

object Name extends ZLiteralTypeCompanion {

  def name = "Name"

}

case class Name( value: String ) extends LiteralValue {
  def indexForm: String = value.toLowerCase.replace(' ', '-')
}