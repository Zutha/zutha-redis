package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literaltype.{FieldCardMax, FieldCardMin}
import net.zutha.redishost.model.singleton.ZFieldClassSingleton
import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.`trait`.FieldDeclarer
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldDeclaration extends ZFieldClassSingleton[ZField, IField, MField] {

  def name = "Field Declaration"

  def apply( declarer: MRef[ZItem],
             role: MRef[ZRole],
             fieldClass: MRef[ZFieldClass],
             cardMin: UnboundedNonNegativeInteger,
             cardMax: UnboundedNonNegativeInteger )
           (implicit acc: MutableAccessor ): NewComplexField = {
    ZField( FieldDeclaration )(
      FieldDeclarer.ref -> declarer, ZRole.ref -> role, ZFieldClass.ref -> fieldClass )(
      FieldCardMin -> cardMin, FieldCardMax -> cardMax )()
  }


}
