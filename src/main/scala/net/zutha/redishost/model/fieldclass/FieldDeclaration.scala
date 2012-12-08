package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literaltype.{FieldCardMax, FieldCardMin}
import net.zutha.redishost.model.companion.ZFieldClassCompanion
import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.`trait`.FieldDeclarer
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldDeclaration extends ZFieldClassCompanion[ZField, IField, MField] {

  def name = "Field Declaration"

  def apply( declarer: MRef[MItem],
             role: MRef[MRole],
             fieldClass: MRef[MFieldClass],
             cardMin: UnboundedNonNegativeInteger,
             cardMax: UnboundedNonNegativeInteger )
           (implicit acc: MutableAccessor ): NewComplexField = {
    ZField( FieldDeclaration )(
      FieldDeclarer.refM -> declarer, ZRole.refM -> role, ZFieldClass.refM -> fieldClass )(
      FieldCardMin -> cardMin, FieldCardMax -> cardMax )()
  }


}
