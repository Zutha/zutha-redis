package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import fieldmember._
import fieldset.{MFieldSetRef, IFieldSetRef}
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._

trait ZComplexField
  extends ZField
  with ZFieldLike[ZComplexField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class ImmutableComplexField protected[redishost] ( id: Zids,
                                                zClass: IRef[ZFieldClass],
                                                fieldSets: Seq[IFieldSetRef],
                                                members: Seq[IFieldMember],
                                                scope: IScopeSeq
                                                )( implicit val acc: ImmutableAccessor )
  extends ZComplexField
  with IFieldLike[ImmutableComplexField]


trait MutableComplexField
  extends ZComplexField
  with MFieldLike[MutableComplexField]

/**
 * A Persisted Field that possibly has unsaved modifications
 */
case class
ModifiedComplexField protected[redishost] ( id: PersistedId,
                                            zClass: MRef[ZFieldClass],
                                            fieldSets: Seq[MFieldSetRef],
                                            rolePlayersOrig: Set[MRolePlayer],
                                            literalsOrig: MLiteralMap,
                                            members: Seq[MFieldMember] = Seq(),
                                            scope: MScopeSeq = Seq(),
                                            messages: Seq[(MsgType, String)] = Seq(),
                                            memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                            scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                            deleted_? : Boolean = false
                                            )( implicit val acc: MutableAccessor )
  extends MutableComplexField
  with ModifiedField
  with MFieldLike[ModifiedComplexField]


/**
 * A Field that has not been persisted to the database
 */
case class NewComplexField protected[redishost] ( id: TempId,
                                                  zClass: MRef[ZFieldClass],
                                                  fieldSets: Seq[MFieldSetRef],
                                                  members: Seq[MFieldMember] = Seq(),
                                                  scope: MScopeSeq = Seq(),
                                                  messages: Seq[(MsgType, String)] = Seq(),
                                                  memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                                  scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                                  deleted_? : Boolean = false
                                                  )( implicit val acc: MutableAccessor )
  extends MutableComplexField
  with NewField
  with MFieldLike[NewComplexField]
