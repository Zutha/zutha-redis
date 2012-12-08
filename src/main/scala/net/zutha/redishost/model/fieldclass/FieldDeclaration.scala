package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literal.{FieldCardMax, FieldCardMin}
import net.zutha.redishost.model.companion.ZFieldClassCompanion
import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.`trait`.FieldDeclarer

object FieldDeclaration extends ZFieldClassCompanion[ZField, IField, MField] {

  def name = "Field Declaration"

  def apply( declarer: MRef[MItem],
             role: MRef[MRole],
             fieldClass: MRef[MFieldClass],
             cardMin: Int,
             cardMax: Int )
           (implicit acc: MutableAccessor ): NewComplexField = {
    ZField( FieldDeclaration )(
      FieldDeclarer.refM -> declarer, ZRole.refM -> role, ZFieldClass.refM -> fieldClass )(
      FieldCardMin.refM -> FieldCardMin(cardMin), FieldCardMax.refM -> FieldCardMax(cardMax) )()
  }


}
