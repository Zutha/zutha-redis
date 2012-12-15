package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literaltype.{FieldCardMax, FieldCardMin}
import net.zutha.redishost.model.singleton.{ZSingleton, ZFieldClassSingleton}
import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.`trait`.ZFieldDeclarer
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

object FieldDeclaration
  extends ZSingleton[ZFieldClass]
  with ZFieldClassSingleton[ZField]
{

  def name = "Field Declaration"

  def apply( declarer: MRef[ZItem],
             role: MRef[ZRole],
             fieldClass: MRef[ZFieldClass],
             cardMin: UnboundedNonNegativeInteger,
             cardMax: UnboundedNonNegativeInteger )
           (implicit acc: MutableAccessor ): NewComplexField = {
    ZComplexField( FieldDeclaration )(
      ZFieldDeclarer.ref -> declarer, ZRole.ref -> role, ZFieldClass.ref -> fieldClass )(
      FieldCardMin -> cardMin, FieldCardMax -> cardMax )()
  }


}
