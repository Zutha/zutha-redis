package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.singleton.ZSingleton

trait SchemaHelpers {
  implicit def acc: MutableAccessor

  private def prelim[B <: ItemBuilder]( ic: ZSingleton[_], b: B ): B = {
    b.name( ic.name )
    b
  }

  def zClass( zClass: ZSingleton[ZClass] ): ClassBuilder =
    prelim( zClass, new ClassBuilder( zClass.refM ) )

  def itemClass( itemClass: ZSingleton[ZItemClass] ): ItemClassBuilder =
    prelim( itemClass, new ItemClassBuilder( itemClass.refM ) )

  def fieldClass( fieldClass: ZSingleton[ZFieldClass] ): FieldClassBuilder =
    prelim( fieldClass, new FieldClassBuilder( fieldClass.refM ) )

  def role( role: ZSingleton[ZRole] ): RoleBuilder =
    prelim( role, new RoleBuilder( role.refM ) )

  def zTrait( zTrait: ZSingleton[ZTrait] ): TraitBuilder =
    prelim( zTrait, new TraitBuilder( zTrait.refM ) )

  def literalType( literalType: ZSingleton[ZLiteralType] ): LiteralTypeBuilder =
    prelim( literalType, new LiteralTypeBuilder( literalType.refM ) )

  def datatype( datatype: ZSingleton[ZDatatype] ): DatatypeBuilder =
    prelim( datatype, new DatatypeBuilder( datatype.refM ) )

  def scopeType( scopeType: ZSingleton[ZScopeType] ): ScopeTypeBuilder =
    prelim( scopeType, new ScopeTypeBuilder( scopeType.refM ) )


}
