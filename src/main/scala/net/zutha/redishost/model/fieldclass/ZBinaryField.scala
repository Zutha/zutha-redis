package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
import fieldmember._
import fieldset.{MFieldSetRef, IFieldSetRef}
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._

trait ZBinaryField
  extends ZField
{
  type T <: ZBinaryField
}

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class
IBinaryField protected[redishost] ( id: Zids,
                                    zClass: IRef[IFieldClass],
                                    fieldSets: Seq[IFieldSetRef],
                                    rolePlayer1: IRolePlayer,
                                    rolePlayer2: IRolePlayer,
                                    scope: IScopeSeq
                                    )( implicit val acc: ImmutableAccessor )
  extends IField
{
  type T = IBinaryField

  def members: Seq[IFieldMember] = Seq( rolePlayer1, rolePlayer2 )
}

trait MBinaryField
  extends ZBinaryField
  with MField
{
  type T <: MBinaryField

  def rolePlayer1: MRolePlayer
  def rolePlayer2: MRolePlayer

  def members: Seq[MFieldMember] = Seq( rolePlayer1, rolePlayer2 )
  override lazy val rolePlayers = Set( rolePlayer1, rolePlayer2 )
  override lazy val literals: MLiteralMap = Map()

  protected def updateRolePlayers( rolePlayers: Set[MRolePlayer] = rolePlayers
                                   ): NewBinaryField = {
    val rps1 = rolePlayers.filter( _.role == rolePlayer1.role)
    val rps2 = rolePlayers.filter( _.role == rolePlayer2.role)
    require(rps1.size <= 1 && rps2.size <= 1, "A Binary Field must have only one player for each role")

    val rp1 = rps1.headOption.getOrElse(rolePlayer1)
    val rp2 = rps2.headOption.getOrElse(rolePlayer2)

    acc.updateField( this, rp1, rp2 )
  }
}

/**
 * A Persisted BinaryField that possibly has unsaved modifications
 */
case class
ModifiedBinaryField protected[redishost] ( id: PersistedId,
                                           zClass: MRef[MFieldClass],
                                           fieldSets: Seq[MFieldSetRef],
                                           rolePlayer1: MRolePlayer,
                                           rolePlayer2: MRolePlayer,
                                           scope: MScopeSeq = Seq(),
                                           messages: Seq[(MsgType, String)] = Seq(),
                                           memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                           scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                           deleted_? : Boolean = false
                                           )( implicit val acc: MutableAccessor )
  extends MBinaryField
  with ModifiedField
{
  type T = ModifiedBinaryField

  // Getters

  def literalsOrig: MLiteralMap = Map()
  def rolePlayersOrig: Set[MRolePlayer] = Set( rolePlayer1, rolePlayer2 )

  //  Mutators

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false
                        ): ModifiedBinaryField = {
    // TODO update using accessor
    ModifiedBinaryField( id, zClass, fieldSets,
      rolePlayer1, rolePlayer2, scope,
      messages, memberMessages, scopeMessages, deleted_? = false )
  }

  /**
   * replaces this (immutable) binary field with a new binary field updated with the provided changes
   * @param rolePlayers role-players pairs to override the existing one(s) with.
   *                    Pairs whose Role does not match one of those in the binary field will be ignored.
   * @param literals this parameter will be ignored for binary fields.
   * @return the deleted old binary field
   */
  protected def updateField ( rolePlayers: Set[MRolePlayer] = rolePlayers,
                              literals: MLiteralMap = literals
                              ): ModifiedBinaryField = {
    updateRolePlayers( rolePlayers )
    acc.getField( this.ref ) // get the deleted old field and return it
  }
}

/**
 * A BinaryField that has not been persisted to the database
 */
case class
NewBinaryField protected[redishost] ( id: TempId,
                                      zClass: MRef[MFieldClass],
                                      fieldSets: Seq[MFieldSetRef],
                                      rolePlayer1: MRolePlayer,
                                      rolePlayer2: MRolePlayer,
                                      scope: MScopeSeq = Seq(),
                                      messages: Seq[(MsgType, String)] = Seq(),
                                      memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                      scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                      deleted_? : Boolean = false
                                      )( implicit val acc: MutableAccessor )
  extends MBinaryField
  with NewField
{
  type T = NewBinaryField

  // Mutators

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false ): NewBinaryField = {

    // TODO update using accessor
    NewBinaryField( id, zClass, fieldSets,
      rolePlayer1, rolePlayer2, scope,
      messages, memberMessages, scopeMessages, deleted_? = false)
  }

  /**
   * updates this new (not yet persisted) binary field with new rolePlayers
   * @param rolePlayers role-players pairs to override the existing one(s) with.
   *                    Pairs whose Role does not match one of those in the binary field will be ignored.
   * @param literals this parameter will be ignored for binary fields.
   * @return the updated binary field
   */
  protected def updateField ( rolePlayers: Set[MRolePlayer] = rolePlayers,
                              literals: MLiteralMap = literals
                              ): NewBinaryField = {
    updateRolePlayers( rolePlayers )
  }
}