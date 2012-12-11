package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.singleton.ZLiteralTypeSingleton
import net.zutha.redishost.model.datatype.URI

object PSI extends ZLiteralTypeSingleton[URI] {

  def name = "PSI"

  protected def datatypeCompanion = URI
}
