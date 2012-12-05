package net.zutha.redishost.model.literal

import net.zutha.redishost.model._
import companion.ZLiteralTypeCompanion
import itemclass.ZLiteralType

object Name extends ZLiteralTypeCompanion {

  def name = "Name"

}

case class Name( value: String ) extends LiteralValue {
  // replace everything but letters and numbers with underscore
  // this form will be used in queries so it should not have any special characters
  def psiForm: String = value.toLowerCase.replaceAll("[^a-z0-9]+", "_")
}