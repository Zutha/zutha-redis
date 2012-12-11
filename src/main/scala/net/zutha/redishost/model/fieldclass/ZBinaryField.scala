package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import fieldmember._
import fieldset.{MFieldSetRef, IFieldSetRef}
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._

trait ZBinaryField
  extends ZField
  with ZFieldLike[ZBinaryField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class
ImmutableBinaryField protected[redishost] ( id: Zids,
                                            zClass: IRef[ZFieldClass],
                                            fieldSets: Seq[IFieldSetRef],
                                            rolePlayer1: IRolePlayer,
                                            rolePlayer2: IRolePlayer,
                                            scope: IScopeSeq
                                            )( implicit val acc: ImmutableAccessor )
  extends ZBinaryField
  with IFieldLike[ImmutableBinaryField]
{

  def members: Seq[IFieldMember] = Seq( rolePlayer1, rolePlayer2 )
}

trait MutableBinaryField
  extends ZBinaryField
  with MFieldLike[MutableBinaryField]
{
  def rolePlayer1: MRolePlayer
  def rolePlayer2: MRolePlayer

  def members: Seq[MFieldMember] = Seq( rolePlayer1, rolePlayer2 )
  override lazy val rolePlayers = Set( rolePlayer1, rolePlayer2 )
  override lazy val literals: MLiteralMap = Map()

}

/**
 * A Persisted BinaryField that possibly has unsaved modifications
 */
case class
ModifiedBinaryField protected[redishost] ( id: PersistedId,
                                           zClass: MRef[ZFieldClass],
                                           fieldSets: Seq[MFieldSetRef],
                                           rolePlayer1: MRolePlayer,
                                           rolePlayer2: MRolePlayer,
                                           scope: MScopeSeq = Seq(),
                                           messages: Seq[(MsgType, String)] = Seq(),
                                           memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                           scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                           deleted_? : Boolean = false
                                           )( implicit val acc: MutableAccessor )
  extends MutableBinaryField
  with ModifiedField
  with MFieldLike[ModifiedBinaryField]
{
  // Getters

  def literalsOrig: MLiteralMap = Map()
  def rolePlayersOrig: Set[MRolePlayer] = Set( rolePlayer1, rolePlayer2 )

}

/**
 * A BinaryField that has not been persisted to the database
 */
case class
NewBinaryField protected[redishost] ( id: TempId,
                                      zClass: MRef[ZFieldClass],
                                      fieldSets: Seq[MFieldSetRef],
                                      rolePlayer1: MRolePlayer,
                                      rolePlayer2: MRolePlayer,
                                      scope: MScopeSeq = Seq(),
                                      messages: Seq[(MsgType, String)] = Seq(),
                                      memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                      scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                      deleted_? : Boolean = false
                                      )( implicit val acc: MutableAccessor )
  extends MutableBinaryField
  with NewField
  with MFieldLike[NewBinaryField]
