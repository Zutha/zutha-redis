package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, MutableAccessor, ImmutableAccessor}

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
    val id = new TempId
    val rps = rolePlayers.toSet
    val literals: MLiteralSet = Set()
    // TODO create with accessor
    val members: List[MFieldMember] = List()
    val fieldSets: List[MFieldSetRef] = List()
    NewField( acc, id, zClass, fieldSets, members, false)
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
//  def fieldSets: List[ZFieldSetRef]
//  def members: List[ZFieldMember]

//  def rolePlayerMap: RolePlayerMap =
//    rolePlayers.groupBy(_._1).mapValues(_.map(_._2))
//
//  def getPlayersOf(role: ReferenceT[ZRole]): Set[ReferenceT[ZObject]] =
//    rolePlayers.filter(_._1 == role).map(_._2)
//
//  def players: Set[ReferenceT[ZObject]] = rolePlayers.map(_._2)
//
//  def literalsOfType(literalType: ReferenceT[ZLiteralType]): LiteralSet =
//    literals.filter(_._2 == literalType)
//
//  def firstLiteralOfType(literalType: ReferenceT[ZLiteralType]): Literal =
//    literalsOfType(literalType).head
}

/**
 * An immutable Field that corresponds to a Field in the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 * @param members
 */
case class IField protected[redishost] ( acc: ImmutableAccessor,
                                         id: Zids,
                                         zClass: IRef[IFieldClass],
                                         fieldSets: List[IFieldSetRef],
                                         members: List[IFieldMember]
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

  lazy val rolePlayers: MRolePlayerSet = {
    val rolePlayerList = members flatMap {m => m match {
      case MFieldRoleMember(role, players) => players.map (p => (role -> p))
      case _ => List()
    }}
    rolePlayerList.toSet
  }

  lazy val literals: MLiteralSet = {
    val literalList = members flatMap {m => m match {
      case MFieldLiteralMember(literalType, values) => values.map (p => (literalType -> p))
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
 *
 * @param id
 * @param zClass
 * @param fieldSets
 * @param rolePlayersOrig
 * @param literalsOrig
 * @param members
 * @param deleted_?
 */
case class
ModifiedField protected[redishost] ( acc: MutableAccessor,
                                     id: PersistedId,
                                     zClass: MRef[MFieldClass],
                                     fieldSets: List[MFieldSetRef],
                                     rolePlayersOrig: MRolePlayerSet,
                                     literalsOrig: MLiteralSet,
                                     members: List[MFieldMember],
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
      rolePlayersOrig, literalsOrig, members, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): ModifiedField = {
    // TODO update using accessor
    ModifiedField( acc, id, zClass, fieldSets,
      rolePlayersOrig, literalsOrig, members, deleted_? )
  }



  def merge(other: ModifiedField): ModifiedField = {
    ??? // need to re-purpose this for merging two different fields (if I end up allowing field merging)
  }

}


/**
 * A Field that has not been persisted to the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 * @param members
 * @param deleted_?
 */
case class NewField protected[redishost] ( acc: MutableAccessor,
                                           id: TempId,
                                           zClass: MRef[MFieldClass],
                                           fieldSets: List[MFieldSetRef],
                                           members: List[MFieldMember],
                                           deleted_? : Boolean = false
                                           )
  extends NewObject
  with MField
{
	type T = NewField

  protected def update( fieldSets: List[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = false ): NewField = {
    // TODO update using accessor
    NewField( acc, id, zClass, fieldSets, members, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): NewField = {
    // TODO update using accessor
    NewField( acc, id, zClass, fieldSets, members, deleted_? )
  }

}
