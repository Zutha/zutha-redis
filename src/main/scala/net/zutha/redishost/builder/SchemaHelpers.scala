package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.SchemaItem
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.fieldclass.MField

trait SchemaHelpers {
  implicit def acc: MutableAccessor

  private def prelim[B <: ItemBuilder]( ic: SchemaItem, b: B ): B = {
    b.name( ic.name )
    b
  }

  def zClass( zClass: SchemaItem{ type ObjTM <: MClass } ): ClassBuilder =
    prelim( zClass, new ClassBuilder( zClass.refM ) )

  def itemClass( itemClass: SchemaItem{ type ObjTM <: MItemClass } ): ItemClassBuilder =
    prelim( itemClass, new ItemClassBuilder( itemClass.refM ) )

  def fieldClass( fieldClass: SchemaItem{ type ObjTM <: MFieldClass } ): FieldClassBuilder =
    prelim( fieldClass, new FieldClassBuilder( fieldClass.refM ) )

  def role( role: SchemaItem{ type ObjTM <: MRole } ): RoleBuilder =
    prelim( role, new RoleBuilder( role.refM ) )

  def zTrait( zTrait: SchemaItem{ type ObjTM <: MTrait } ): TraitBuilder =
    prelim( zTrait, new TraitBuilder( zTrait.refM ) )

  def literalType( literalType: SchemaItem{ type ObjTM <: MLiteralType } ): LiteralTypeBuilder =
    prelim( literalType, new LiteralTypeBuilder( literalType.refM ) )

  def datatype( datatype: SchemaItem{ type ObjTM <: MDatatype } ): DatatypeBuilder =
    prelim( datatype, new DatatypeBuilder( datatype.refM ) )

  def scopeType( scopeType: SchemaItem{ type ObjTM <: MScopeType } ): ScopeTypeBuilder =
    prelim( scopeType, new ScopeTypeBuilder( scopeType.refM ) )


}
