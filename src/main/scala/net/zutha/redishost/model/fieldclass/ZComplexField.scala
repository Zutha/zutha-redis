package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import fieldset.{MFieldSetRef, IFieldSetRef}
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._

trait ZComplexField
  extends ZField
{
  type T <: ZComplexField
}

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
{
  type T = IComplexField
}

trait MComplexField
  extends ZComplexField
  with MField
{
  type T <: MComplexField
}

/**
 * A Persisted Field that possibly has unsaved modifications
 */
case class
ModifiedComplexField protected[redishost] ( id: PersistedId,
                                            zClass: MRef[MFieldClass],
                                            fieldSets: Seq[MFieldSetRef],
                                            rolePlayersOrig: MRolePlayerSet,
                                            literalsOrig: MLiteralSet,
                                            members: Seq[MFieldMember] = Seq(),
                                            scope: MScopeSeq = Seq(),
                                            messages: Seq[(MsgType, String)] = Seq(),
                                            memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                            scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                            deleted_? : Boolean = false
                                            )( implicit val acc: MutableAccessor )
  extends MComplexField
  with ModifiedField
{
  type T = ModifiedComplexField

  //  Mutators

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false
                        ): ModifiedComplexField = {
    // TODO update using accessor
    ModifiedComplexField( id, zClass, fieldSets,
      rolePlayersOrig, literalsOrig, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerMap = rolePlayers,
                              literals: MLiteralMap = literals
                              ): ModifiedComplexField = {
    // TODO update using accessor
    ModifiedComplexField( id, zClass, fieldSets,
      rolePlayersOrig, literalsOrig, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
}


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
{
  type T = NewComplexField

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false ): NewComplexField = {
    // TODO update using accessor
    NewComplexField( id, zClass, fieldSets, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerMap = rolePlayers,
                              literals: MLiteralMap = literals
                              ): NewComplexField = {
    // TODO update using accessor
    NewComplexField( id, zClass, fieldSets, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
}