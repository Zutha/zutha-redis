package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import fieldmember._
import fieldset.{MFieldSetRef, IFieldSetRef}
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._

object ZComplexField {

  /**
   * Create a new Complex Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers the rolePlayers of the field
   * @param literals the literal members of the field
   * @return
   */
  def apply( zClass: MRef[ZFieldClass] )
           ( rolePlayers: MRolePlayer* )
           ( literals: MLiteralFieldMember* )
           ( scope: (MRef[ZScopeType], Set[MRef[ZObject]])* )
           ( implicit acc: MA ): NewComplexField = {
    acc.createField( zClass, rolePlayers.toSet, literals.toSet, scope.toMap ) match {
      case f: NewComplexField => f
      case f => throw new IllegalArgumentException(
        "This constructor should only be used for Complex Fields. Field created: " + f.toString )
    }
  }

}

trait ZComplexField
  extends ZField
  with Loadable[ZComplexField, ZField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class ImmutableComplexField protected[redishost] ( id: Zids,
                                                        zClass: IRef[ZFieldClass],
                                                        fieldSets: Seq[IFieldSetRef],
                                                        members: Seq[IFieldMember],
                                                        scope: IScopeSeq )
                                                      ( implicit val acc: ImmutableAccessor)
  extends ZComplexField
  with IField
  with Loadable[ImmutableComplexField, ZField]


trait MutableComplexField
  extends ZComplexField
  with MField
  with Loadable[MutableComplexField, ZField]

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
                                            deleted_? : Boolean = false )
                                          ( implicit val acc: MutableAccessor )
  extends MutableComplexField
  with ModifiedField
  with Loadable[ModifiedComplexField, ZField]


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
                                                  deleted_? : Boolean = false )
                                                ( implicit val acc: MutableAccessor )
  extends MutableComplexField
  with NewField
  with Loadable[NewComplexField, ZField]
