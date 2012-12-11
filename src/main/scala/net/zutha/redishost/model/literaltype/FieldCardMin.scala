package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.singleton.ZLiteralTypeSingleton
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldCardMin extends ZLiteralTypeSingleton[UnboundedNonNegativeInteger] {

  def name = "Field Card Min"

  protected def datatypeCompanion = UnboundedNonNegativeInteger
}