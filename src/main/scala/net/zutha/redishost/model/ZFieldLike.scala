package net.zutha.redishost.model

import fieldclass.ZField
import fieldmember._
import fieldmember.MLiteral
import fieldmember.MRoleFieldMember
import fieldmember.MRolePlayer
import fieldset._
import itemclass._
import scala.reflect.runtime.universe._
import net.zutha.redishost.model.MsgType._


trait ZFieldLike[+This <: ZField]
  extends ZObjectLike[This]
{
  self: This =>

  // Accessors

  def zClass: Ref[ZObject, ZFieldClass]

  def members: Seq[ZFieldMember]

  def scope: ScopeSeq
}

trait IFieldLike[+This <: IField]
  extends ZFieldLike[This]
  with IObjectLike[This]
{
  self: This =>

  // Accessors

  def zClass: IRef[ZFieldClass]

  def members: Seq[IFieldMember]

  def scope: IScopeSeq
}

trait MFieldLike[+This <: MField]
  extends ZFieldLike[This]
  with MObjectLike[This]
{
  self: This =>

  // Accessors

  def zClass: MRef[ZFieldClass]

  def members: Seq[MFieldMember]

  def scope: MScopeSeq

  def memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]]

  def scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]]

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

  /**
   * Applies changes to the rolePlayers and Literals of a field.
   * Only the RoleMembers and LiteralMembers that are allowed by the Field's Class will be affected by this operation.
   * Any rolePlayers or literals given that are not permitted by the FieldClass will be ignored.
   * @param rolePlayers
   * @param literals
   * @return
   */
  protected def updateField[T >: L <: MField: TypeTag]
  ( rolePlayers: Set[MRolePlayer] = rolePlayers,
    literals: MLiteralMap = literals
    ): T = {
    acc.updateField( this.ref, rolePlayers, literals )
    reload[T]
  }

  // Mutators

  def mutateRolePlayers[T >: L <: MField: TypeTag]( mutate: Set[MRolePlayer] => Set[MRolePlayer] ): T =
    updateField[T]( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer[T >: L <: MField: TypeTag]( rolePlayer: MRolePlayer ): T =
    mutateRolePlayers[T](_ + rolePlayer )

  def removeRolePlayer[T >: L <: MField: TypeTag]( rolePlayer: MRolePlayer ) : T =
    mutateRolePlayers[T]( _ - rolePlayer )

  def mutateLiterals[T >: L <: MField: TypeTag]( mutate: MLiteralMap => MLiteralMap ): T =
    updateField[T]( literals = mutate(literals) )

  def updateLiteral[T >: L <: MField: TypeTag]( literalType: MRef[ZLiteralType],
                                                   newValue: LiteralValue
                                                   ): T = {
    mutateLiterals[T]( _.updated( literalType, newValue ) )
  }

  def applyDiff[T >: L <: MField: TypeTag]( diff: ZFieldDiff ): T = {
    val newRolePlayers = rolePlayers ++ diff.addedRolePlayers -- diff.removedRolePlayers
    val newLiterals = literals ++ diff.modifiedLiterals

    updateField[T]( rolePlayers = newRolePlayers, literals = newLiterals )
  }
}