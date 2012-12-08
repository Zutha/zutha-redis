package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.companion.ZLiteralTypeCompanion
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldCardMax extends ZLiteralTypeCompanion[UnboundedNonNegativeInteger] {

  def name = "Field Card Max"

  protected def datatypeCompanion = UnboundedNonNegativeInteger
}