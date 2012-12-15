package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import singleton.{ZSingleton, ZFieldClassSingleton}
import fieldmember._
import fieldset._
import itemclass._

object ZField
  extends ZSingleton[ZFieldClass]
  with ZFieldClassSingleton[ZField] {

  def name = "Field"


  /**
   * Create a new Field consisting only of rolePlayers
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers the rolePlayers of the field
   * @return
   */
  def apply( zClass: MRef[ZFieldClass],
             rolePlayers: MRolePlayer* )
           ( implicit acc: MA ): NewField = {
    apply( zClass )( rolePlayers:_* )()()
  }

  /**
   * Create a new Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers the rolePlayers of the field
   * @param literals the literal members of the field
   * @return
   */
  def apply( zClass: MRef[ZFieldClass] )
           ( rolePlayers: MRolePlayer* )
           ( literals: MLiteralFieldMember* )
           ( scope: (MRef[ZScopeType], Set[MRef[ZObject]])* )
           ( implicit acc: MA ): NewField = {
    acc.createField( zClass, rolePlayers.toSet, literals.toSet, scope.toMap )
  }
}

/**
 * An association between one or more objects and zero or more literal values
 */
trait ZField
  extends ZObject
  with Referenceable[ZField]


/**
 * An immutable Field
 */
trait IField
  extends ZField
  with IObject
  with IFieldLike
  with Loadable[IField, ZField]


/**
 * A Mutable Field
 */
trait MField
  extends ZField
  with MObject
  with MFieldLike
  with Loadable[MField, ZField]


/**
 * A Persisted Field that possibly has unsaved modifications
 */
trait ModifiedField
  extends MField
  with ModifiedObject
  with Loadable[ModifiedField, ZField]
{
  def rolePlayersOrig: Set[RolePlayer[A]]
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
  extends MField
  with NewObject
  with Loadable[NewField, ZField]
