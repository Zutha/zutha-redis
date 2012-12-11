package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import MsgType._
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}

trait ZPropertyField
  extends ZField
  with ZFieldLike[ZPropertyField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class
ImmutablePropertyField protected[redishost] ( id: Zids,
                                      zClass: IRef[ZFieldClass],
                                      fieldSets: Seq[IFieldSetRef],
                                      rolePlayer: IRolePlayer,
                                      literal: ILiteral,
                                      scope: IScopeSeq
                                      )( implicit val acc: ImmutableAccessor )
  extends ZPropertyField
  with IFieldLike[ImmutablePropertyField]
{
  def members: Seq[IFieldMember] = Seq( rolePlayer, literal )
}

trait MutablePropertyField
  extends ZPropertyField
  with MFieldLike[MutablePropertyField]
{

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
                                             zClass: MRef[ZFieldClass],
                                             fieldSets: Seq[MFieldSetRef],
                                             rolePlayer: MRolePlayer,
                                             literalOrig: MLiteral,
                                             literal: MLiteral,
                                             scope: MScopeSeq = Seq(),
                                             messages: Seq[(MsgType, String)] = Seq(),
                                             memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                             scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                             deleted_? : Boolean = false
                                             )( implicit val acc: MutableAccessor )
  extends MutablePropertyField
  with ModifiedField
  with MFieldLike[ModifiedPropertyField]
{
  // Getters

  def literalsOrig: MLiteralMap = Set( literalOrig )
  def rolePlayersOrig: Set[MRolePlayer] = Set( rolePlayer )

}


/**
 * A PropertyField that has not been persisted to the database
 */
case class
NewPropertyField protected[redishost] ( id: TempId,
                                        zClass: MRef[ZFieldClass],
                                        fieldSets: Seq[MFieldSetRef],
                                        rolePlayer: MRolePlayer,
                                        literal: MLiteral,
                                        scope: MScopeSeq = Seq(),
                                        messages: Seq[(MsgType, String)] = Seq(),
                                        memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                        scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                        deleted_? : Boolean = false
                                        )( implicit val acc: MutableAccessor )
  extends MutablePropertyField
  with NewField
  with MFieldLike[NewPropertyField]
