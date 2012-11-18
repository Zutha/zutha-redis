package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._


object ZField extends ObjectFactory[ZField, IField, MField] {
  def name = "ZField"

  def validType_?(obj: ZObject): Boolean = ???

  /**
   * Create a new Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers
   * @return
   */
  def apply( zClass: MRef[MFieldClass],
             rolePlayers: MRolePlayer*
             )( implicit acc: MutableAccessor ): NewField = {
    val rps = rolePlayers.groupBy(_._1).mapValues(_.map(_._2).toSet)
    val literals: MLiteralMap = Map()
    val scope: MScopeMap = Map()
    acc.createField( zClass, rps, literals, scope )
  }
}

/**
 * An association between one or more objects and zero or more literal values
 */
trait ZField extends ZObject
{
	type T <: ZField

  def id: ZIdentity

  def zClass: ZRef[ZFieldClass]
  def members: Seq[ZFieldMember]
  def scope: ScopeSeq
}

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class IField protected[redishost] ( id: Zids,
                                         zClass: IRef[IFieldClass],
                                         fieldSets: Seq[IFieldSetRef],
                                         members: Seq[IFieldMember],
                                         scope: IScopeSeq
                                         )( implicit val acc: ImmutableAccessor )
  extends IObject
  with ZField
{
  type T = IField
}

/**
 * A Field that can be Modified
 */
trait MField
  extends ZField
  with MObject
{
	type T <: MField

  override def id: ZIdentity

  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): T

  // Accessors

  def zClass: MRef[MFieldClass]
  def fieldSets: Seq[MFieldSetRef]
  def members: Seq[MFieldMember]
  def scope: MScopeSeq
  def memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]]
  def scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]]

  lazy val rolePlayers: MRolePlayerSet = {
    val rolePlayerSeq = members flatMap {m => m match {
      case MRoleFieldMember(role, players) => players.map (p => (role -> p))
      case _ => Seq()
    }}
    rolePlayerSeq.toSet
  }

  lazy val literals: MLiteralSet = {
    val literalSeq = members flatMap {m => m match {
      case MLiteralFieldMember(literalType, values) => values.map (p => (literalType -> p))
      case _ => Seq()
    }}
    literalSeq.toSet
  }

  // Mutators

  def mutateRolePlayers(mutate: MRolePlayerSet => MRolePlayerSet): T =
    updateField( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer(role: MRole, player: MObject): T =
    mutateRolePlayers(_ + (role.ref -> player.ref) )

  def removeRolePlayer(role: MRole, player: MObject): T =
    mutateRolePlayers( _ - (role.ref -> player.ref) )

  def mutateLiterals(mutate: MLiteralSet => MLiteralSet): T =
    updateField( literals = mutate(literals) )

  def addLiteral(literal: MLiteral): T =
    mutateLiterals( _ + literal )

  def removeLiteral(literal: MLiteral): T =
    mutateLiterals( _ - literal )

  def applyDiff(diff: ZFieldDiff): T = {
    val newRolePlayers = rolePlayers ++ diff.addedRolePlayers -- diff.removedRolePlayers
    val newLiterals = literals ++ diff.addedLiterals -- diff.removedLiterals

    updateField( rolePlayers = newRolePlayers, literals = newLiterals )
  }
}

/**
 * A Persisted Field that possibly has unsaved modifications
 */
case class
ModifiedField protected[redishost] ( id: PersistedId,
                                     zClass: MRef[MFieldClass],
                                     fieldSets: Seq[MFieldSetRef],
                                     rolePlayersOrig: MRolePlayerSet,
                                     literalsOrig: MLiteralSet,
                                     members: Seq[MFieldMember] = Seq(),
                                     scope: MScopeSeq = Seq(),
                                     messages: Seq[(MsgType, String)] = Seq(),
                                     memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]],
                                     scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]],
                                     deleted_? : Boolean = false
                                     )( implicit val acc: MutableAccessor )
  extends ModifiedObject
  with MField
{
	type T = ModifiedField

  // Accessors

  def calcDiff: ZFieldDiff = {
    val addedRolePlayers = rolePlayers -- rolePlayersOrig
    val removedRolePlayers = rolePlayersOrig -- rolePlayers
    val addedLiterals = literals -- literalsOrig
    val removedLiterals = literalsOrig -- literals
    val modifiedLiterals = Set[MLiteral]() //TODO use for unary literals
    ZFieldDiff(addedRolePlayers, removedRolePlayers,
      addedLiterals, removedLiterals, modifiedLiterals)
  }

  //  Mutators

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false
                        ): ModifiedField = {
    // TODO update using accessor
    ModifiedField( id, zClass, fieldSets,
      rolePlayersOrig, literalsOrig, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): ModifiedField = {
    // TODO update using accessor
    ModifiedField( id, zClass, fieldSets,
      rolePlayersOrig, literalsOrig, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }



  def merge(other: ModifiedField): ModifiedField = {
    ??? // need to re-purpose this for merging two different fields (if I end up allowing field merging)
  }

}


/**
 * A Field that has not been persisted to the database
 */
case class NewField protected[redishost] ( id: TempId,
                                           zClass: MRef[MFieldClass],
                                           fieldSets: Seq[MFieldSetRef],
                                           members: Seq[MFieldMember] = Seq(),
                                           scope: MScopeSeq = Seq(),
                                           messages: Seq[(MsgType, String)] = Seq(),
                                           memberMessages: Map[MRef[MFieldMemberType], Seq[(MsgType, String)]] = Map(),
                                           scopeMessages: Map[MRef[MScopeType], Seq[(MsgType, String)]] = Map(),
                                           deleted_? : Boolean = false
                                           )( implicit val acc: MutableAccessor )
  extends NewObject
  with MField
{
	type T = NewField

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false ): NewField = {
    // TODO update using accessor
    NewField( id, zClass, fieldSets, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): NewField = {
    // TODO update using accessor
    NewField( id, zClass, fieldSets, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }

}
