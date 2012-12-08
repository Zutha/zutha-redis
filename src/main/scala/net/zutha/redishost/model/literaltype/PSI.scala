package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.companion.ZLiteralTypeCompanion
import net.zutha.redishost.model.datatype.URI

object PSI extends ZLiteralTypeCompanion[URI] {

  def name = "PSI"

  protected def datatypeCompanion = URI
}
