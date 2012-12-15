package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.singleton.{ZSingleton, ZLiteralTypeSingleton}
import net.zutha.redishost.model.datatype.URI
import net.zutha.redishost.model.itemclass.ZLiteralType

object PSI
  extends ZSingleton[ZLiteralType]
  with ZLiteralTypeSingleton[URI]
{

  def name = "PSI"

  protected def datatypeCompanion = URI
}
