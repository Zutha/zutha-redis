package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, MutableAccessor, ImmutableAccessor}

object ZField extends ZObjectFactory[ZField, ZImmutableField, ZMutableField] {
  def typeName = "ZField"

  def validType_?(obj: ZConcreteObject): Boolean = ???

  /**
   * Create a new Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers
   * @return
   */
  def apply[A <: MutableAccessor]( acc: A,
                                   zClass: MReferenceT[A, ZMFieldClass],
                                   rolePlayers: MRolePlayer[A]*
                                   ): ZNewField[A] = {
    val id = new TempId
    val rps = rolePlayers.toSet
    val fieldSets: MFieldSetMap[A] = Map() //TODO get from db (implement in ZObject)
    val literals: MLiteralSet[A] = Set()
    ZNewField( acc, id, zClass, fieldSets, rps, literals, false)
  }
}

/**
 * An association between one or more objects and zero or more literal values
 */
trait ZField extends ZObject with HasRef[ZObject] {

  def id: ZFieldIdentity
  def zClass: ReferenceT[ZFieldClass]
  def fieldSets: FieldSetMap
  def rolePlayers: RolePlayerSet
  def literals: LiteralSet

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
case class ZImmutableField
[A <: ImmutableAccessor] protected[redishost] ( acc: A,
                                                id: Zid,
                                                zClass: IReferenceT[A, ZIFieldClass],
                                                fieldSets: IFieldSetMap[A],
                                                rolePlayers: IRolePlayerSet[A],
                                                literals: ILiteralSet[A]
                                                )
  extends ZConcreteImmutableObject[A]
  with ZField
  with HasImmutableRef[A, ZImmutableField[A]]
{

}

/**
 * A Field that can be Modified
 */
trait ZMutableField[A <: MutableAccessor]
  extends ZField
  with ZConcreteMutableObject[A]
  with HasMutableRef[A, ZMutableField[A]]
{
  override type T <: ZMutableField[A]

  protected def updateField ( rolePlayers: MRolePlayerSet[A] = rolePlayers,
                              literals: MLiteralSet[A] = literals
                              ): T

  def zClass: MReferenceT[A, ZMFieldClass]
  def fieldSets: MFieldSetMap[A]
  def rolePlayers: MRolePlayerSet[A]
  def literals: MLiteralSet[A]

  def mutateRolePlayers(mutate: MRolePlayerSet[A] => MRolePlayerSet[A]): T =
    updateField( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer(role: ZMRole[A], player: ZMutableObject[A]): T =
    mutateRolePlayers(_ + (role.ref -> player.ref) )

  def removeRolePlayer(role: ZMRole[A], player: ZMutableObject[A]): T =
    mutateRolePlayers( _ - (role.ref -> player.ref) )

  def mutateLiterals(mutate: MLiteralSet[A] => MLiteralSet[A]): T =
    updateField( literals = mutate(literals) )

  def addLiteral(literal: MLiteral[A]): T =
    mutateLiterals( _ + literal )

  def removeLiteral(literal: MLiteral[A]): T =
    mutateLiterals( _ - literal )

  def applyDiff(diff: ZFieldDiff[A]): T = {
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
ZModifiedField[A <: MutableAccessor] protected[redishost] ( acc: A,
                                                            id: Zid,
                                                            zClass: MReferenceT[A, ZMFieldClass],
                                                            fieldSets: MFieldSetMap[A],
                                                            rolePlayersBkp: MRolePlayerSet[A],
                                                            rolePlayers: MRolePlayerSet[A],
                                                            literalsBkp: MLiteralSet[A],
                                                            literals: MLiteralSet[A],
                                                            deleted_? : Boolean = false
                                                            )
  extends ZModifiedObject[A]
  with ZMutableField[A]
  with HasMutableRef[A, ZModifiedField[A]]
{

  override type T = ZModifiedField[A]

  // Accessors

  def calcDiff: ZFieldDiff[A] = {
    val addedRolePlayers = rolePlayers -- rolePlayersBkp
    val removedRolePlayers = rolePlayersBkp -- rolePlayers
    val addedLiterals = literals -- literalsBkp
    val removedLiterals = literalsBkp -- literals
    val modifiedLiterals = Set[MLiteral[A]]() //TODO unary literals and text diffs
    ZFieldDiff(addedRolePlayers, removedRolePlayers,
      addedLiterals, removedLiterals, modifiedLiterals)
  }

  //  Mutators

  protected def update( fieldSets: MFieldSetMap[A] = fieldSets,
                        deleted_? : Boolean = false
                        ): ZModifiedField[A] = {
    ZModifiedField( acc, id, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet[A] = rolePlayers,
                              literals: MLiteralSet[A] = literals
                              ): ZModifiedField[A] = {
    ZModifiedField( acc, id, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }



  def merge(other: ZModifiedField[A]): ZModifiedField[A] = {
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
case class ZNewField
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                               id: TempId,
                                               zClass: MReferenceT[A, ZMFieldClass],
                                               fieldSets: MFieldSetMap[A],
                                               rolePlayers: MRolePlayerSet[A],
                                               literals: MLiteralSet[A],
                                               deleted_? : Boolean = false
                                               )
  extends ZNewObject[A]
  with ZMutableField[A]
  with HasMutableRef[A, ZNewField[A]]
{
  override type T = ZNewField[A]

  protected def update( fieldSets: MFieldSetMap[A] = fieldSets,
                        deleted_? : Boolean = false ): ZNewField[A] = {
    ZNewField( acc, id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: MRolePlayerSet[A] = rolePlayers,
                              literals: MLiteralSet[A] = literals
                              ): ZNewField[A] = {
    ZNewField( acc, id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }

}
