package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.companion.ZLiteralTypeCompanion
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldCardMin extends ZLiteralTypeCompanion[UnboundedNonNegativeInteger] {

  def name = "Field Card Min"

  protected def datatypeCompanion = UnboundedNonNegativeInteger
}