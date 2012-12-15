package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.singleton.{ZSingleton, ZLiteralTypeSingleton}
import net.zutha.redishost.model.datatype.ZString
import net.zutha.redishost.model.itemclass.ZLiteralType

object Name
  extends ZSingleton[ZLiteralType]
  with ZLiteralTypeSingleton[ZString]
{

  def name = "Name"

  protected def datatypeCompanion = ZString

  // replace everything but letters and numbers with underscore
  // this form will be used in queries so it should not have any special characters
  def indexForm( name: ZString ) : String = name.value.toLowerCase.replaceAll("[^a-z0-9]+", "_")
}
