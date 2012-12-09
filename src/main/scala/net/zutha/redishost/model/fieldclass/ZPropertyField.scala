package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import MsgType._
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}

trait ZPropertyField
  extends ZField
{
  type T <: ZPropertyField
}

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class
IPropertyField protected[redishost] ( id: Zids,
                                      zClass: IRef[IFieldClass],
                                      fieldSets: Seq[IFieldSetRef],
                                      rolePlayer: IRolePlayer,
                                      literal: ILiteral,
                                      scope: IScopeSeq
                                      )( implicit val acc: ImmutableAccessor )
  extends IField
{
  type T = IPropertyField

  def members: Seq[IFieldMember] = Seq( rolePlayer, literal )
}

trait MPropertyField
  extends ZPropertyField
  with MField
{
  type T <: MPropertyField

  def rolePlayer: MRolePlayer
  def literal: MLiteral

  def members: Seq[MFieldMember] = Seq( rolePlayer, literal )
  override lazy val rolePlayers = Set( rolePlayer )
  override lazy val literals = Map( literal.toPair )
}

/**
 * A Persisted PropertyField that possibly has unsaved modifications
 */
case class
ModifiedPropertyField protected[redishost] ( id: PersistedId,
                                             zClass: MRef[MFieldClass],
                                             fieldSets: Seq[MFieldSetRef],
                                             rolePlayer: MRolePlayer,
                                             literalOrig: MLiteral,
                                             literal: MLiteral,
                                             scope: MScopeSeq = Seq(),
                                             messages: Seq[(MsgType, String)] = Seq(),
                                             memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                             scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                             deleted_? : Boolean = false
                                             )( implicit val acc: MutableAccessor )
  extends MPropertyField
  with ModifiedField
{
  type T = ModifiedPropertyField

  // Getters

  def literalsOrig: MLiteralMap = Set( literalOrig )
  def rolePlayersOrig: Set[MRolePlayer] = Set( rolePlayer )

  //  Mutators

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false
                        ): ModifiedPropertyField = {
    // TODO update using accessor
    ModifiedPropertyField( id, zClass, fieldSets,
      rolePlayer, literalOrig, literal, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: Set[MRolePlayer] = rolePlayers,
                              literals: MLiteralMap = literals
                              ): ModifiedPropertyField = {
    // TODO update using accessor
    ModifiedPropertyField( id, zClass, fieldSets,
      rolePlayer, literalOrig, literal, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
}


/**
 * A PropertyField that has not been persisted to the database
 */
case class
NewPropertyField protected[redishost] ( id: TempId,
                                        zClass: MRef[MFieldClass],
                                        fieldSets: Seq[MFieldSetRef],
                                        rolePlayer: MRolePlayer,
                                        literal: MLiteral,
                                        scope: MScopeSeq = Seq(),
                                        messages: Seq[(MsgType, String)] = Seq(),
                                        memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                        scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                        deleted_? : Boolean = false
                                        )( implicit val acc: MutableAccessor )
  extends MPropertyField
  with NewField
{
  type T = NewPropertyField

  // Mutators

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false ): NewPropertyField = {
    // TODO update using accessor
    NewPropertyField( id, zClass, fieldSets,
      rolePlayer, literal, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: Set[MRolePlayer] = rolePlayers,
                              literals: MLiteralMap = literals
                              ): NewPropertyField = {
    // TODO update using accessor
    NewPropertyField( id, zClass, fieldSets,
      rolePlayer, literal, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
}