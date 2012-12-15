package net.zutha.redishost.model

import fieldclass.{IField, MField, ZField}
import fieldmember._
import fieldset._
import itemclass._
import scala.reflect.runtime.universe._
import net.zutha.redishost.model.MsgType._


trait ZFieldLike
  extends ZObjectLike
  with Loadable[ZField, ZField]
{
  self: ZField =>

  // Accessors

  def zClass: ZRef[A, ZFieldClass]

  def members: Seq[FieldMember[A]]

  def scope: ScopeSeq[A]
}

trait IFieldLike
  extends ZFieldLike
  with IObjectLike
  with Loadable[IField, ZField]
{
  self: IField =>

  // Accessors

  def zClass: IRef[ZFieldClass]

  def members: Seq[IFieldMember]

  def scope: IScopeSeq
}

trait MFieldLike
  extends ZFieldLike
  with MObjectLike
  with Loadable[MField, ZField]
{
  self: MField =>

  // Accessors

  def zClass: MRef[ZFieldClass]

  def members: Seq[MFieldMember]

  def scope: MScopeSeq

  def memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]]

  def scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]]

  lazy val rolePlayers: MRolePlayerSet = {
    val rolePlayerSeq: Seq[MRolePlayer] = members flatMap { m => m match {
      case RoleFieldMember( role, players ) => players.map (p => RolePlayer(role, p))
      case _ => Seq()
    }}
    rolePlayerSeq.toSet
  }

  lazy val literals: MLiteralMap = members.collect {
    case literal: MLiteralFieldMember => literal.toPair
  }.toMap

  /**
   * Applies changes to the rolePlayers and Literals of a field.
   * Only the RoleMembers and LiteralMembers that are allowed by the Field's Class will be affected by this operation.
   * Any rolePlayers or literals given that are not permitted by the FieldClass will be ignored.
   * @param rolePlayers
   * @param literals
   * @return
   */
  protected def updateField[T >: Impl <: MField: TypeTag]
  ( rolePlayers: Set[MRolePlayer] = rolePlayers,
    literals: MLiteralMap = literals
    ): T = {
    acc.updateField( this.zRef, rolePlayers, literals )
    reload
  }

  // Mutators

  def mutateRolePlayers[T >: Impl <: MField: TypeTag]( mutate: Set[MRolePlayer] => Set[MRolePlayer] ): T =
    updateField[T]( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer[T >: Impl <: MField: TypeTag]( rolePlayer: MRolePlayer ): T =
    mutateRolePlayers[T](_ + rolePlayer )

  def removeRolePlayer[T >: Impl <: MField: TypeTag]( rolePlayer: MRolePlayer ) : T =
    mutateRolePlayers[T]( _ - rolePlayer )

  def mutateLiterals[T >: Impl <: MField: TypeTag]( mutate: MLiteralMap => MLiteralMap ): T =
    updateField[T]( literals = mutate(literals) )

  def updateLiteral[T >: Impl <: MField: TypeTag]( literalType: MRef[ZLiteralType],
                                                   newValue: LiteralValue
                                                   ): T = {
    mutateLiterals[T]( _.updated( literalType, newValue ) )
  }

  def applyDiff[T >: Impl <: MField: TypeTag]( diff: ZFieldDiff ): T = {
    val newRolePlayers = rolePlayers ++ diff.addedRolePlayers -- diff.removedRolePlayers
    val newLiterals = literals ++ diff.modifiedLiterals

    updateField[T]( rolePlayers = newRolePlayers, literals = newLiterals )
  }
}