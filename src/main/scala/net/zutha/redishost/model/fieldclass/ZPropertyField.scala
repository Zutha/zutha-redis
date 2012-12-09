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
IPropertyField protected[redishost] ( id: Zids,
                                      zClass: IRef[IFieldClass],
                                      fieldSets: Seq[IFieldSetRef],
                                      rolePlayer: IRolePlayer,
                                      literal: ILiteral,
                                      scope: IScopeSeq
                                      )( implicit val acc: ImmutableAccessor )
  extends IField
  with IFieldLike[IPropertyField]
{
  def members: Seq[IFieldMember] = Seq( rolePlayer, literal )
}

trait MPropertyField
  extends ZPropertyField
  with MField
  with MFieldLike[MPropertyField]
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
  with MFieldLike[NewPropertyField]
