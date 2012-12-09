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
case class IComplexField protected[redishost] ( id: Zids,
                                                zClass: IRef[IFieldClass],
                                                fieldSets: Seq[IFieldSetRef],
                                                members: Seq[IFieldMember],
                                                scope: IScopeSeq
                                                )( implicit val acc: ImmutableAccessor )
  extends ZComplexField
  with IField
  with IFieldLike[IComplexField]


trait MComplexField
  extends ZComplexField
  with MField
  with MFieldLike[MComplexField]

/**
 * A Persisted Field that possibly has unsaved modifications
 */
case class
ModifiedComplexField protected[redishost] ( id: PersistedId,
                                            zClass: MRef[MFieldClass],
                                            fieldSets: Seq[MFieldSetRef],
                                            rolePlayersOrig: Set[MRolePlayer],
                                            literalsOrig: MLiteralMap,
                                            members: Seq[MFieldMember] = Seq(),
                                            scope: MScopeSeq = Seq(),
                                            messages: Seq[(MsgType, String)] = Seq(),
                                            memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                            scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                            deleted_? : Boolean = false
                                            )( implicit val acc: MutableAccessor )
  extends MComplexField
  with ModifiedField
  with MFieldLike[ModifiedComplexField]


/**
 * A Field that has not been persisted to the database
 */
case class NewComplexField protected[redishost] ( id: TempId,
                                                  zClass: MRef[MFieldClass],
                                                  fieldSets: Seq[MFieldSetRef],
                                                  members: Seq[MFieldMember] = Seq(),
                                                  scope: MScopeSeq = Seq(),
                                                  messages: Seq[(MsgType, String)] = Seq(),
                                                  memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                                  scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                                  deleted_? : Boolean = false
                                                  )( implicit val acc: MutableAccessor )
  extends MComplexField
  with NewField
  with MFieldLike[NewComplexField]
