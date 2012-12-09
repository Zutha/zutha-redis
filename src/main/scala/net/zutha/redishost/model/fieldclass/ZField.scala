package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model._
import companion.ZFieldClassCompanion
import fieldmember._
import fieldset._
import itemclass._
import MsgType._
import net.zutha.redishost.exception.SchemaException

object ZField extends ZFieldClassCompanion[ZField, IField, MField] {

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
{
	type T <: ZField

  def id: ZIdentity

  def zClass: ZRef[ZFieldClass]
  def members: Seq[ZFieldMember]
  def scope: ScopeSeq
}

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
trait IField
  extends ZField
  with IObject
{
  type T <: IField

  def id: Zids
  def zClass: IRef[IFieldClass]
  def fieldSets: Seq[IFieldSetRef]
  def members: Seq[IFieldMember]
  def scope: IScopeSeq
}

/**
 * A Field that can be Modified
 */
trait MField
  extends ZField
  with MObject
{
	type T <: MField

  override def id: ZIdentity

  /**
   * Applies changes to the rolePlayers and Literals of a field.
   * Only the RoleMembers and LiteralMembers that are allowed by the Field's Class will be affected by this operation.
   * Any rolePlayers or literals given that are not permitted by the FieldClass will be ignored.
   * @param rolePlayers
   * @param literals
   * @return
   */
  protected def updateField ( rolePlayers: Set[MRolePlayer] = rolePlayers,
                              literals: MLiteralMap = literals
                              ): T

  // Accessors

  def zClass: MRef[MFieldClass]
  def fieldSets: Seq[MFieldSetRef]
  def members: Seq[MFieldMember]
  def scope: MScopeSeq
  def memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]]
  def scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]]

  lazy val rolePlayers: Set[MRolePlayer] = {
    val rolePlayerSeq: Seq[MRolePlayer] = members flatMap { m => m match {
      case MRoleFieldMember( role, players ) => players.map (p => MRolePlayer(role, p))
      case _ => Seq()
    }}
    rolePlayerSeq.toSet
  }

  lazy val literals: MLiteralMap = members.collect {
    case literal: MLiteral => literal.toPair
  }.toMap

  // Mutators

  def mutateRolePlayers(mutate: Set[MRolePlayer] => Set[MRolePlayer]): T =
    updateField( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer( rolePlayer: MRolePlayer ): T =
    mutateRolePlayers(_ + rolePlayer )

  def removeRolePlayer( rolePlayer: MRolePlayer ) : T =
    mutateRolePlayers( _ - rolePlayer )

  def mutateLiterals( mutate: MLiteralMap => MLiteralMap ): T =
    updateField( literals = mutate(literals) )

  def updateLiteral( literalType: MRef[MLiteralType], newValue: LiteralValue ): T = {
    mutateLiterals( _.updated( literalType, newValue ) )
  }

  def applyDiff( diff: ZFieldDiff ): T = {
    val newRolePlayers = rolePlayers ++ diff.addedRolePlayers -- diff.removedRolePlayers
    val newLiterals = literals ++ diff.modifiedLiterals

    updateField( rolePlayers = newRolePlayers, literals = newLiterals )
  }
}

/**
 * A Persisted Field that possibly has unsaved modifications
 */
trait ModifiedField
  extends ModifiedObject
  with MField
{
  type T <: ModifiedField

  def id: PersistedId
  def zClass: MRef[MFieldClass]
  def fieldSets: Seq[MFieldSetRef]
  def rolePlayersOrig: Set[MRolePlayer]
  def literalsOrig: MLiteralMap
  def members: Seq[MFieldMember]
  def scope: MScopeSeq
  def messages: Seq[(MsgType, String)]
  def memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]]
  def scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]]
  def deleted_? : Boolean

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
  type T <: NewField

  def id: TempId
  def zClass: MRef[MFieldClass]
  def fieldSets: Seq[MFieldSetRef]
  def members: Seq[MFieldMember]
  def scope: MScopeSeq
  def messages: Seq[(MsgType, String)]
  def memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]]
  def scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]]
  def deleted_? : Boolean
}
