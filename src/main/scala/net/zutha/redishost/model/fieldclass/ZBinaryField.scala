package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import fieldmember._
import fieldset.{MFieldSetRef, IFieldSetRef}
import itemclass._
import net.zutha.redishost.db.ImmutableAccessor
import net.zutha.redishost.model.MsgType._
import net.zutha.redishost.exception.SchemaException

object ZBinaryField {

  /**
   * Create a new Binary Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayer1 one of the two rolePlayers of this binary field
   * @param rolePlayer2 the other of the two rolePlayers of this binary field
   */
  def apply( zClass: MRef[ZFieldClass],
             rolePlayer1: MRolePlayer,
             rolePlayer2: MRolePlayer,
             scope: (MRef[ZScopeType], Set[MRef[ZObject]])* )
           ( implicit acc: MA ): NewBinaryField = {
    //TODO verify that field class is a binary field
    acc.createField(zClass, Set(rolePlayer1, rolePlayer2), Map(), scope.toMap ) match {
      case f: NewBinaryField => f
      case f => throw new SchemaException(
        "createdField should have returned a BinaryField. Actually returned: " + f.toString )
    }
  }

}

trait ZBinaryField
  extends ZField
  with Loadable[ZBinaryField, ZField]

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class
ImmutableBinaryField protected[redishost] ( zid: Zid,
                                            allZids: Seq[Zid],
                                            zClass: IRef[ZFieldClass],
                                            fieldSets: Seq[IFieldSetRef],
                                            rolePlayer1: IRolePlayer,
                                            rolePlayer2: IRolePlayer,
                                            scope: IScopeSeq )
                                          ( implicit val acc: ImmutableAccessor )
  extends ZBinaryField
  with IField
  with Loadable[ImmutableBinaryField, ZField]
{
  def members: Seq[IFieldMember] = Seq( rolePlayer1, rolePlayer2 )
}

trait MutableBinaryField
  extends ZBinaryField
  with MField
  with Loadable[MutableBinaryField, ZField]
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
ModifiedBinaryField protected[redishost] ( primaryZids: Seq[Zid],
                                           allZids: Seq[Zid],
                                           zClass: MRef[ZFieldClass],
                                           fieldSets: Seq[MFieldSetRef],
                                           rolePlayer1: MRolePlayer,
                                           rolePlayer2: MRolePlayer,
                                           scope: MScopeSeq = Seq(),
                                           messages: Seq[(MsgType, String)] = Seq(),
                                           memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                           scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                           deleted_? : Boolean = false )
                                         ( implicit val acc: MA )
  extends MutableBinaryField
  with ModifiedField
  with Loadable[ModifiedBinaryField, ZField]
{
  // Getters

  def literalsOrig: MLiteralMap = Map()
  def rolePlayersOrig: Set[MRolePlayer] = Set( rolePlayer1, rolePlayer2 )

}

/**
 * A BinaryField that has not been persisted to the database
 */
case class
NewBinaryField protected[redishost] ( key: String,
                                      zClass: MRef[ZFieldClass],
                                      fieldSets: Seq[MFieldSetRef],
                                      rolePlayer1: MRolePlayer,
                                      rolePlayer2: MRolePlayer,
                                      scope: MScopeSeq = Seq(),
                                      messages: Seq[(MsgType, String)] = Seq(),
                                      memberMessages: Map[MRef[ZFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                      scopeMessages: Map[MRef[ZScopeType], Seq[(MsgType, String)]] = Map(),
                                      deleted_? : Boolean = false )
                                    ( implicit val acc: MA )
  extends MutableBinaryField
  with NewField
  with Loadable[NewBinaryField, ZField]