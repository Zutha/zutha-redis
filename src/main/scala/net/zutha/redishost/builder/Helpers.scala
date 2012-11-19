package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.MutableAccessor

trait Helpers {

  implicit def acc: MutableAccessor

  def zClassB( zClass: MRef[MClass] ): ClassBuilder =
    new ClassBuilder( zClass )

  def zClass( zClass: MRef[MClass] )( f: ClassBuilder => Unit ) {
    f( zClassB( zClass ) )
  }

  def itemClassB( itemClass: MRef[MItemClass] ): ItemClassBuilder =
    new ItemClassBuilder( itemClass )

  def itemClass( itemClass: MRef[MItemClass] )( f: ItemClassBuilder => Unit ) {
    f( itemClassB( itemClass ) )
  }

  def fieldClassB( fieldClass: MRef[MFieldClass] ): FieldClassBuilder =
    new FieldClassBuilder( fieldClass )

  def fieldClass( fieldClass: MRef[MFieldClass] )( f: FieldClassBuilder => Unit ) {
    f( fieldClassB( fieldClass ) )
  }

  def roleB( role: MRef[MRole] ): RoleBuilder =
    new RoleBuilder( role )

  def role( role: MRef[MRole] )( f: RoleBuilder => Unit ) {
    f( roleB( role ) )
  }

  def zTraitB( zTrait: MRef[MTrait] ): TraitBuilder =
    new TraitBuilder( zTrait )

  def zTrait( zTrait: MRef[MTrait] )( f: TraitBuilder => Unit ) {
    f( zTraitB( zTrait ) )
  }

  def literalTypeB( literalType: MRef[MLiteralType] ): LiteralTypeBuilder =
    new LiteralTypeBuilder( literalType )

  def literalType( literalType: MRef[MLiteralType] )( f: LiteralTypeBuilder => Unit ) {
    f( literalTypeB( literalType ) )
  }

  def datatypeB( datatype: MRef[MDatatype] ): DatatypeBuilder =
    new DatatypeBuilder( datatype )

  def datatype( datatype: MRef[MDatatype] )( f: DatatypeBuilder => Unit ) {
    f( datatypeB( datatype ) )
  }

  def scopeTypeB( scopeType: MRef[MScopeType] ): ScopeTypeBuilder =
    new ScopeTypeBuilder( scopeType )

  def scopeType( scopeType: MRef[MScopeType] )( f: ScopeTypeBuilder => Unit ) {
    f( scopeTypeB( scopeType ) )
  }
}
