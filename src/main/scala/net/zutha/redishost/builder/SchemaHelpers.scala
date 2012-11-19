package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.SchemaItem
import net.zutha.redishost.model.itemclass._

trait SchemaHelpers {
  implicit def acc: MutableAccessor

  private def prelim[B <: ItemBuilder]( ic: SchemaItem, b: B ): B = {
    b.name( ic.name )
    b
  }

  def zClassB( zClass: SchemaItem{ type ObjTM <: MClass } ): ClassBuilder =
    prelim( zClass, new ClassBuilder( zClass.refM ) )

  def zClass( zClass: SchemaItem{ type ObjTM <: MClass } )( f: ClassBuilder => Unit ) {
    f( zClassB( zClass ) )
  }

  def itemClassB( itemClass: SchemaItem{ type ObjTM <: MItemClass } ): ItemClassBuilder =
    prelim( itemClass, new ItemClassBuilder( itemClass.refM ) )

  def itemClass( itemClass: SchemaItem{ type ObjTM <: MItemClass } )( f: ItemClassBuilder => Unit ) {
    f( itemClassB( itemClass ) )
  }

  def fieldClassB( fieldClass: SchemaItem{ type ObjTM <: MFieldClass } ): FieldClassBuilder =
    prelim( fieldClass, new FieldClassBuilder( fieldClass.refM ) )

  def fieldClass( fieldClass: SchemaItem{ type ObjTM <: MFieldClass } )( f: FieldClassBuilder => Unit ) {
    f( fieldClassB( fieldClass ) )
  }

  def roleB( role: SchemaItem{ type ObjTM <: MRole } ): RoleBuilder =
    prelim( role, new RoleBuilder( role.refM ) )

  def role( role: SchemaItem{ type ObjTM <: MRole } )( f: RoleBuilder => Unit ) {
    f( roleB( role ) )
  }

  def zTraitB( zTrait: SchemaItem{ type ObjTM <: MTrait } ): TraitBuilder =
    prelim( zTrait, new TraitBuilder( zTrait.refM ) )

  def zTrait( zTrait: SchemaItem{ type ObjTM <: MTrait } )( f: TraitBuilder => Unit ) {
    f( zTraitB( zTrait ) )
  }

  def literalTypeB( literalType: SchemaItem{ type ObjTM <: MLiteralType } ): LiteralTypeBuilder =
    prelim( literalType, new LiteralTypeBuilder( literalType.refM ) )

  def literalType( literalType: SchemaItem{ type ObjTM <: MLiteralType } )( f: LiteralTypeBuilder => Unit ) {
    f( literalTypeB( literalType ) )
  }

  def datatypeB( datatype: SchemaItem{ type ObjTM <: MDatatype } ): DatatypeBuilder =
    prelim( datatype, new DatatypeBuilder( datatype.refM ) )

  def datatype( datatype: SchemaItem{ type ObjTM <: MDatatype } )( f: DatatypeBuilder => Unit ) {
    f( datatypeB( datatype ) )
  }

  def scopeTypeB( scopeType: SchemaItem{ type ObjTM <: MScopeType } ): ScopeTypeBuilder =
    prelim( scopeType, new ScopeTypeBuilder( scopeType.refM ) )

  def scopeType( scopeType: SchemaItem{ type ObjTM <: MScopeType } )( f: ScopeTypeBuilder => Unit ) {
    f( scopeTypeB( scopeType ) )
  }
}
