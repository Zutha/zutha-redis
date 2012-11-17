package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._


object ZField extends ZObjectFactory[ZField, IField, MField] {
  def typeName = "ZField"

  def validType_?(obj: ZObject): Boolean = ???

  /**
   * Create a new Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers
   * @return
   */
  def apply( acc: MutableAccessor,
             zClass: MRef[MFieldClass],
             rolePlayers: MRolePlayer*
             ): NewField = {
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
  def members: List[ZFieldMember]
  def scope: ScopeList
}

/**
 * An immutable Field that corresponds to a Field in the database
 *
 */
case class IField protected[redishost] ( acc: ImmutableAccessor,
                                         id: Zids,
                                         zClass: IRef[IFieldClass],
                                         fieldSets: List[IFieldSetRef],
                                         members: List[IFieldMember],
                                         scope: IScopeList
                                         )
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
  def fieldSets: List[MFieldSetRef]
  def members: List[MFieldMember]
  def scope: MScopeList
  def memberMessages: Map[MRef[MFieldMemberType], List[(MsgType, String)]]
  def scopeMessages: Map[MRef[MScopeType], List[(MsgType, String)]]

  lazy val rolePlayers: MRolePlayerSet = {
    val rolePlayerList = members flatMap {m => m match {
      case MRoleFieldMember(role, players) => players.map (p => (role -> p))
      case _ => List()
    }}
    rolePlayerList.toSet
  }

  lazy val literals: MLiteralSet = {
    val literalList = members flatMap {m => m match {
      case MLiteralFieldMember(literalType, values) => values.map (p => (literalType -> p))
      case _ => List()
    }}
    literalList.toSet
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
ModifiedField protected[redishost] ( acc: MutableAccessor,
                                     id: PersistedId,
                                     zClass: MRef[MFieldClass],
                                     fieldSets: List[MFieldSetRef],
                                     rolePlayersOrig: MRolePlayerSet,
                                     literalsOrig: MLiteralSet,
                                     members: List[MFieldMember] = List(),
                                     scope: MScopeList = List(),
                                     messages: List[(MsgType, String)] = List(),
                                     memberMessages: Map[MRef[MFieldMemberType], List[(MsgType, String)]],
                                     scopeMessages: Map[MRef[MScopeType], List[(MsgType, String)]],
                                     deleted_? : Boolean = false
                                     )
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

  protected def update( fieldSets: List[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false
                        ): ModifiedField = {
    // TODO update using accessor
    ModifiedField( acc, id, zClass, fieldSets,
      rolePlayersOrig, literalsOrig, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): ModifiedField = {
    // TODO update using accessor
    ModifiedField( acc, id, zClass, fieldSets,
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
case class NewField protected[redishost] ( acc: MutableAccessor,
                                           id: TempId,
                                           zClass: MRef[MFieldClass],
                                           fieldSets: List[MFieldSetRef],
                                           members: List[MFieldMember] = List(),
                                           scope: MScopeList = List(),
                                           messages: List[(MsgType, String)] = List(),
                                           memberMessages: Map[MRef[MFieldMemberType], List[(MsgType, String)]] = Map(),
                                           scopeMessages: Map[MRef[MScopeType], List[(MsgType, String)]] = Map(),
                                           deleted_? : Boolean = false
                                           )
  extends NewObject
  with MField
{
	type T = NewField

  protected def update( fieldSets: List[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false ): NewField = {
    // TODO update using accessor
    NewField( acc, id, zClass, fieldSets, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): NewField = {
    // TODO update using accessor
    NewField( acc, id, zClass, fieldSets, members, scope,
      messages, memberMessages, scopeMessages, deleted_? )
  }

}
