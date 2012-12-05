package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model._
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
  override lazy val literals: MLiteralSet = Set()


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

  def literalsOrig: MLiteralSet = Set()
  def rolePlayersOrig: MRolePlayerSet = Set( rolePlayer1, rolePlayer2 )

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
  protected def updateField ( rolePlayers: MRolePlayerMap = rolePlayers,
                              literals: MLiteralMap = literals
                              ): ModifiedBinaryField = {
    val rps1 = rolePlayers.get(rolePlayer1._1).getOrElse(Set(rolePlayer1._2))
    val rps2 = rolePlayers.get(rolePlayer2._1).getOrElse(Set(rolePlayer2._2))
    require(rps1.size == 1 && rps2.size == 1, "A Binary Field must have exactly one player for each role")

    val rp1 = (rolePlayer1._1 -> rps1.head)
    val rp2 = (rolePlayer2._1 -> rps2.head)
    acc.createField( zClass, Set(rp1, rp2), Set(), scope )

    // TODO delete using accessor
    ModifiedBinaryField( id, zClass, fieldSets,
      rolePlayer1, rolePlayer2, scope,
      messages, memberMessages, scopeMessages, deleted_? = true )
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
  protected def updateField ( rolePlayers: MRolePlayerMap = rolePlayers,
                              literals: MLiteralMap = literals
                              ): NewBinaryField = {
    val rps1 = rolePlayers.get(rolePlayer1._1).getOrElse(Set(rolePlayer1._2))
    val rps2 = rolePlayers.get(rolePlayer2._1).getOrElse(Set(rolePlayer2._2))
    require(rps1.size == 1 && rps2.size == 1, "A Binary Field must have exactly one player for each role")

    val rp1 = (rolePlayer1._1 -> rps1.head)
    val rp2 = (rolePlayer2._1 -> rps2.head)
//    acc.createBinaryField( zClass, rp1, rp2, scope )
    // TODO update using accessor
    NewBinaryField( id, zClass, fieldSets,
      rp1, rp2, scope,
      messages, memberMessages, scopeMessages, deleted_? = false )
  }
}