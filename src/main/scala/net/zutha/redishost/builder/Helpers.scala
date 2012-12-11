package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.MutableAccessor

trait Helpers {

  implicit def acc: MutableAccessor

  def field( field: MRef[ZField] ): FieldBuilder =
    new FieldBuilder( field )

  def zClass( zClass: MRef[ZClass] ): ClassBuilder =
    new ClassBuilder( zClass )

  def itemClass( itemClass: MRef[ZItemClass] ): ItemClassBuilder =
    new ItemClassBuilder( itemClass )

  def fieldClass( fieldClass: MRef[ZFieldClass] ): FieldClassBuilder =
    new FieldClassBuilder( fieldClass )

  def role( role: MRef[ZRole] ): RoleBuilder =
    new RoleBuilder( role )

  def zTrait( zTrait: MRef[ZTrait] ): TraitBuilder =
    new TraitBuilder( zTrait )

  def literalType( literalType: MRef[ZLiteralType] ): LiteralTypeBuilder =
    new LiteralTypeBuilder( literalType )

  def datatype( datatype: MRef[ZDatatype] ): DatatypeBuilder =
    new DatatypeBuilder( datatype )

  def scopeType( scopeType: MRef[ZScopeType] ): ScopeTypeBuilder =
    new ScopeTypeBuilder( scopeType )

}
