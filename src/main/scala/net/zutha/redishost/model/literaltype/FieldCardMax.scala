package net.zutha.redishost.model.literaltype

import net.zutha.redishost.model.singleton.{ZSingleton, ZLiteralTypeSingleton}
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger
import net.zutha.redishost.model.itemclass.ZLiteralType

object FieldCardMax
  extends ZSingleton[ZLiteralType]
  with ZLiteralTypeSingleton[UnboundedNonNegativeInteger] {

  def name = "Field Card Max"

  protected def datatypeCompanion = UnboundedNonNegativeInteger
}