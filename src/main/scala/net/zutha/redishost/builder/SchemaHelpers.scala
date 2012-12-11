package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.singleton.ZItemSingleton

trait SchemaHelpers {
  implicit def acc: MutableAccessor

  private def prelim[B <: ItemBuilder]( ic: ZItemSingleton, b: B ): B = {
    b.name( ic.name )
    b
  }

  def zClass( zClass: ZItemSingleton{ type ObjTM <: MClass } ): ClassBuilder =
    prelim( zClass, new ClassBuilder( zClass.refM ) )

  def itemClass( itemClass: ZItemSingleton{ type ObjTM <: MItemClass } ): ItemClassBuilder =
    prelim( itemClass, new ItemClassBuilder( itemClass.refM ) )

  def fieldClass( fieldClass: ZItemSingleton{ type ObjTM <: MFieldClass } ): FieldClassBuilder =
    prelim( fieldClass, new FieldClassBuilder( fieldClass.refM ) )

  def role( role: ZItemSingleton{ type ObjTM <: MRole } ): RoleBuilder =
    prelim( role, new RoleBuilder( role.refM ) )

  def zTrait( zTrait: ZItemSingleton{ type ObjTM <: MTrait } ): TraitBuilder =
    prelim( zTrait, new TraitBuilder( zTrait.refM ) )

  def literalType( literalType: ZItemSingleton{ type ObjTM <: MLiteralType } ): LiteralTypeBuilder =
    prelim( literalType, new LiteralTypeBuilder( literalType.refM ) )

  def datatype( datatype: ZItemSingleton{ type ObjTM <: MDatatype } ): DatatypeBuilder =
    prelim( datatype, new DatatypeBuilder( datatype.refM ) )

  def scopeType( scopeType: ZItemSingleton{ type ObjTM <: MScopeType } ): ScopeTypeBuilder =
    prelim( scopeType, new ScopeTypeBuilder( scopeType.refM ) )


}
