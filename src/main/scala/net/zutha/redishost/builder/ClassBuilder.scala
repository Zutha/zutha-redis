package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.{NewComplexField, FieldDeclaration}

class ClassBuilder( val ref: MRef[MClass] )
                  ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[ClassBuilder]
{
  def playsRole( role: MRef[MRole],
                 fieldClass: MRef[MFieldClass],
                 minCard: Int,
                 maxCard: Int ): MRef[NewComplexField] = {
    FieldDeclaration( ref, role, fieldClass, minCard, maxCard ).ref
  }
}
