package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.MField

trait Helpers {

  implicit def acc: MutableAccessor

  def field( field: MRef[MField] ): FieldBuilder =
    new FieldBuilder( field )

  def zClass( zClass: MRef[MClass] ): ClassBuilder =
    new ClassBuilder( zClass )

  def itemClass( itemClass: MRef[MItemClass] ): ItemClassBuilder =
    new ItemClassBuilder( itemClass )

  def fieldClass( fieldClass: MRef[MFieldClass] ): FieldClassBuilder =
    new FieldClassBuilder( fieldClass )

  def role( role: MRef[MRole] ): RoleBuilder =
    new RoleBuilder( role )

  def zTrait( zTrait: MRef[MTrait] ): TraitBuilder =
    new TraitBuilder( zTrait )

  def literalType( literalType: MRef[MLiteralType] ): LiteralTypeBuilder =
    new LiteralTypeBuilder( literalType )

  def datatype( datatype: MRef[MDatatype] ): DatatypeBuilder =
    new DatatypeBuilder( datatype )

  def scopeType( scopeType: MRef[MScopeType] ): ScopeTypeBuilder =
    new ScopeTypeBuilder( scopeType )

}
