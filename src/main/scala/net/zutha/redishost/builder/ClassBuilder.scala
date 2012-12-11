package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.{NewComplexField, FieldDeclaration}
import net.zutha.redishost.model.datatype.UnboundedNonNegativeInteger

class ClassBuilder( val ref: MRef[ZClass] )
                  ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[ClassBuilder]
{
  def playsRole( role: MRef[ZRole],
                 fieldClass: MRef[ZFieldClass],
                 minCard: UnboundedNonNegativeInteger,
                 maxCard: UnboundedNonNegativeInteger
                 ): MRef[NewComplexField] = {
    FieldDeclaration( ref, role, fieldClass, minCard, maxCard ).ref
  }
}
