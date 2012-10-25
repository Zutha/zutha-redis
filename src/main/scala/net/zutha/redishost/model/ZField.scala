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
             zClass: MRefT[MFieldClass],
             rolePlayers: MRolePlayer*
             ): NewField = {
    val id = new TempId
    val rps = rolePlayers.toSet
    val fieldSets: MFieldSetMap = Map() //TODO get from db (implement in ZObject)
    val literals: MLiteralSet = Set()
    NewField( acc, id, zClass, fieldSets, rps, literals, false)
  }
}

/**
 * An association between one or more objects and zero or more literal values
 */
trait ZField extends ZObject
{
	type T <: ZField

  def id: ZIdentity

  def zClass: RefT[ZFieldClass]
//  def fieldSets: FieldSetMap
//  def rolePlayers: RolePlayerSet
//  def literals: LiteralSet

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
 * @param rolePlayers
 * @param literals
 */
case class IField protected[redishost] ( acc: ImmutableAccessor,
                                         id: Zids,
                                         zClass: IRefT[IFieldClass],
                                         fieldSets: IFieldSetMap,
                                         rolePlayers: IRolePlayerSet,
                                         literals: ILiteralSet
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

  def zClass: MRefT[MFieldClass]
  def fieldSets: MFieldSetMap
  def rolePlayers: MRolePlayerSet
  def literals: MLiteralSet

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
 * @param rolePlayersBkp
 * @param rolePlayers
 * @param literalsBkp
 * @param literals
 * @param deleted_?
 */
case class
ModifiedField protected[redishost] ( acc: MutableAccessor,
                                     id: PersistedId,
                                     zClass: MRefT[MFieldClass],
                                     fieldSets: MFieldSetMap,
                                     rolePlayersBkp: MRolePlayerSet,
                                     rolePlayers: MRolePlayerSet,
                                     literalsBkp: MLiteralSet,
                                     literals: MLiteralSet,
                                     deleted_? : Boolean = false
                                     )
  extends ModifiedObject
  with MField
{
	type T = ModifiedField

  // Accessors

  def calcDiff: ZFieldDiff = {
    val addedRolePlayers = rolePlayers -- rolePlayersBkp
    val removedRolePlayers = rolePlayersBkp -- rolePlayers
    val addedLiterals = literals -- literalsBkp
    val removedLiterals = literalsBkp -- literals
    val modifiedLiterals = Set[MLiteral]() //TODO unary literals and text diffs
    ZFieldDiff(addedRolePlayers, removedRolePlayers,
      addedLiterals, removedLiterals, modifiedLiterals)
  }

  //  Mutators

  protected def update( fieldSets: MFieldSetMap = fieldSets,
                        deleted_? : Boolean = false
                        ): ModifiedField = {
    ModifiedField( acc, id, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): ModifiedField = {
    ModifiedField( acc, id, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
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
 * @param rolePlayers
 * @param literals
 * @param deleted_?
 */
case class NewField protected[redishost] ( acc: MutableAccessor,
                                               id: TempId,
                                               zClass: MRefT[MFieldClass],
                                               fieldSets: MFieldSetMap,
                                               rolePlayers: MRolePlayerSet,
                                               literals: MLiteralSet,
                                               deleted_? : Boolean = false
                                               )
  extends NewObject
  with MField
{
	type T = NewField

  protected def update( fieldSets: MFieldSetMap = fieldSets,
                        deleted_? : Boolean = false ): NewField = {
    NewField( acc, id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet = rolePlayers,
                              literals: MLiteralSet = literals
                              ): NewField = {
    NewField( acc, id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }

}
