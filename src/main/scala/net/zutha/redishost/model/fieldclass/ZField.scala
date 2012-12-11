package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model._
import singleton.ZFieldClassSingleton
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.exception.SchemaException

object ZField extends ZFieldClassSingleton[ZField, IField, MField] {

  def name = "Field"

  /**
   * Create a new Property Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayer the rolePlayer pointing to the property parent item
   * @param literal the literal value of the property field
   */
  def apply( zClass: MRef[MFieldClass],
             rolePlayer: MRolePlayer,
             literal: MLiteral,
             scope: (MRef[MScopeType], Set[MRef[MObject]])* )
           ( implicit acc: MutableAccessor ): NewPropertyField = {
    //TODO verify that field class is a property field
    val scopeMap: MScopeMap = scope.toMap
    acc.createField(zClass, Set(rolePlayer), Set(literal), scopeMap.toMap ) match {
      case f: NewPropertyField => f
      case f => throw new SchemaException(
        "createdField should have returned a PropertyField. Actually returned: " + f.toString )
    }
  }

  /**
   * Create a new Binary Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayer1 one of the two rolePlayers of this binary field
   * @param rolePlayer2 the other of the two rolePlayers of this binary field
   */
  def apply( zClass: MRef[MFieldClass],
             rolePlayer1: MRolePlayer,
             rolePlayer2: MRolePlayer,
             scope: (MRef[MScopeType], Set[MRef[MObject]])* )
           ( implicit acc: MutableAccessor ): NewBinaryField = {
    //TODO verify that field class is a binary field
    acc.createField(zClass, Set(rolePlayer1, rolePlayer2), Map(), scope.toMap ) match {
      case f: NewBinaryField => f
      case f => throw new SchemaException(
        "createdField should have returned a BinaryField. Actually returned: " + f.toString )
    }
  }

  /**
   * Create a new Field consisting only of rolePlayers
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers the rolePlayers of the field
   * @return
   */
  def apply( zClass: MRef[MFieldClass], 
             rolePlayers: MRolePlayer* )
           ( implicit acc: MutableAccessor ): NewComplexField = {
    apply( zClass )( rolePlayers:_* )()()
  }

  /**
   * Create a new Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers the rolePlayers of the field
   * @param literals the literal members of the field
   * @return
   */
  def apply( zClass: MRef[MFieldClass] )
           ( rolePlayers: MRolePlayer* )
           ( literals: MLiteral* )
           ( scope: (MRef[MScopeType], Set[MRef[MObject]])* )
           ( implicit acc: MutableAccessor ): NewComplexField = {
    acc.createField( zClass, rolePlayers.toSet, literals.toSet, scope.toMap ) match {
      case f: NewComplexField => f
      case f => throw new IllegalArgumentException(
        "This constructor should only be used for Complex Fields. Field created: " + f.toString )
    }
  }
}

/**
 * An association between one or more objects and zero or more literal values
 */
trait ZField extends ZObject
  with ZFieldLike[ZField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
trait IField
  extends ZField
  with IObject
  with IFieldLike[IField]


/**
 * A Field that can be Modified
 */
trait MField
  extends ZField
  with MObject
  with MFieldLike[MField]


/**
 * A Persisted Field that possibly has unsaved modifications
 */
trait ModifiedField
  extends ModifiedObject
  with MField
  with MFieldLike[ModifiedField]
{

  def id: PersistedId

  def rolePlayersOrig: Set[MRolePlayer]
  def literalsOrig: MLiteralMap


  // Accessors

  def calcDiff: ZFieldDiff = {
    val addedRolePlayers = rolePlayers -- rolePlayersOrig
    val removedRolePlayers = rolePlayersOrig -- rolePlayers
    val modifiedLiterals = literals filter { case (literalType, value) =>
      value != literalsOrig( literalType )
    }
    ZFieldDiff( addedRolePlayers, removedRolePlayers, modifiedLiterals )
  }

  // Mutators

  def merge(other: ModifiedField): ModifiedField = {
    ??? // need to re-purpose this for merging two different fields (if I end up allowing field merging)
  }

}


/**
 * A Field that has not been persisted to the database
 */
trait NewField
  extends NewObject
  with MField
{

}
