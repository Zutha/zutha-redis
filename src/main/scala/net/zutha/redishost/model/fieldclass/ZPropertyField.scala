package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import MsgType._
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaException


object ZPropertyField {

  /**
   * Create a new Property Field
   * @param fieldClass the FieldClass of the new Field
   * @param rolePlayer the rolePlayer pointing to the property parent item
   * @param literal the literal value of the property field
   */
  def apply( fieldClass: MRef[ZFieldClass],
             rolePlayer: MRolePlayer,
             literal: MLiteralFieldMember,
             scope: (MRef[ZScopeType], Set[MRef[ZObject]])* )
           ( implicit acc: MutableAccessor ): NewPropertyField = {
    //TODO verify that field class is a property field
    val scopeMap: MScopeMap = scope.toMap
    acc.createField( fieldClass, Set(rolePlayer), Set(literal), scopeMap.toMap ) match {
      case f: NewPropertyField => f
      case f => throw new SchemaException(
        "createdField should have returned a PropertyField. Actually returned: " + f.toString )
    }
  }
}

trait ZPropertyField
  extends ZField
  with Loadable[ZPropertyField, ZField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class
ImmutablePropertyField protected[redishost] ( id: Zids,
                                              zClass: IRef[ZFieldClass],
                                              fieldSets: Seq[IFieldSetRef],
                                              rolePlayer: IRolePlayer,
                                              literal: ILiteralFieldMember,
                                              scope: IScopeSeq )
                                            (implicit val acc: ImmutableAccessor)
  extends ZPropertyField
  with IField
  with Loadable[ImmutablePropertyField, ZField]
{
  def members: Seq[IFieldMember] = Seq( rolePlayer, literal )
}

trait MutablePropertyField
  extends ZPropertyField
  with MField
  with Loadable[MutablePropertyField, ZField]
{

  def rolePlayer: MRolePlayer
  def literal: MLiteralFieldMember

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
                                             literalOrig: MLiteralFieldMember,
                                             literal: MLiteralFieldMember,
                                             scope: MScopeSeq = Seq(),
                                             messages: Seq[(MsgType, String)] = Seq(),
                                             memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                             scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                             deleted_? : Boolean = false )
                                           ( implicit val acc: MutableAccessor)
  extends MutablePropertyField
  with ModifiedField
  with Loadable[ModifiedPropertyField, ZField]
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
                                        literal: MLiteralFieldMember,
                                        scope: MScopeSeq = Seq(),
                                        messages: Seq[(MsgType, String)] = Seq(),
                                        memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                        scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                        deleted_? : Boolean = false )
                                      ( implicit val acc: MutableAccessor )
  extends MutablePropertyField
  with NewField
  with Loadable[NewPropertyField, ZField]
