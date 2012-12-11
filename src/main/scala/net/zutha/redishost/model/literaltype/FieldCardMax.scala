package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.singleton.ZLiteralTypeSingleton
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldCardMax extends ZLiteralTypeSingleton[UnboundedNonNegativeInteger] {

  def name = "Field Card Max"

  protected def datatypeCompanion = UnboundedNonNegativeInteger
}