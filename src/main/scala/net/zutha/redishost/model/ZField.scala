package net.zutha.redishost.model

import net.zutha.redishost.db.DB
import common._

object ZField {
  def get( id: SingleZid ): ZPersistedField = ???

  /**
   * Create a new Field
   * @param zClass the FieldClass of the new Field
   * @param rolePlayers
   * @return
   */
  def apply( zClass: ZFieldClass,
             rolePlayers: RolePlayer*
             ): ZNewField = {
    val id = new TempId
    val rps = rolePlayers.toSet
    val fieldSets: FieldSetMap[ZNewFieldSet] = Map() //TODO get from db (implement in ZObject)
    ZNewField( id, zClass, fieldSets, rps, Set() )
  }
}

/**
 * An association between one or more objects and zero or more literal values
 */
trait ZField extends ZObject {

  def id: ZFieldIdentity
  def zClass: ZFieldClass
  def fieldSets: FieldSetMap[ZFieldSet]
  def rolePlayers: RolePlayerSet
  def literals: LiteralSet

  def rolePlayerMap: Map[ZRole, Set[ZObject]] =
    rolePlayers.groupBy(_._1).mapValues(_.map(_._2))

  def getPlayersOf(role: ZRole): Set[ZObject] =
    rolePlayers.filter(_._1 == role).map(_._2)

  def players: Set[ZObject] = rolePlayers.map(_._2)

  def literalsOfType(literalType: ZLiteralType): LiteralSet =
    literals.filter(_._2 == literalType)

  def firstLiteralOfType(literalType: ZLiteralType): Literal =
    literalsOfType(literalType).head
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
case class
ZPersistedField protected[model] ( id: SingleZid,
                                   zClass: ZFieldClass,
                                   fieldSets: FieldSetMap[ZPersistedFieldSet],
                                   rolePlayers: RolePlayerSet,
                                   literals: LiteralSet
                                   ) extends ZPersistedObject with ZField {

  override def edit: ZModifiedField =
    ZModifiedField( id, zClass, fieldSets.mapValues(_.edit),
    rolePlayers, rolePlayers, literals, literals )

  def applyDiff(diff: ZFieldDiff): ZModifiedField = {
    edit.applyDiff(diff)
  }
  def merge(other: ZModifiedField): ZModifiedField = {
    other merge this
  }

  def reload(limit: Int) = ???
}

/**
 * A Field that can be Modified
 */
trait ZMutableField extends ZField with ZMutableObject {
  type T <: ZMutableField

  protected
  def updateField ( rolePlayers: RolePlayerSet = rolePlayers,
                    literals: LiteralSet = literals
                    ): T

  override def save: Option[ZPersistedField]
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
ZModifiedField protected[model] ( id: SingleZid,
                                  zClass: ZFieldClass,
                                  fieldSets: FieldSetMap[ZModifiedFieldSet],
                                  rolePlayersBkp: RolePlayerSet,
                                  rolePlayers: RolePlayerSet,
                                  literalsBkp: LiteralSet,
                                  literals: LiteralSet,
                                  deleted_? : Boolean = false
                                  )
  extends ZModifiedObject with ZField with ZMutableField {

  type T = ZModifiedField

  // Accessors

  def calcDiff: ZFieldDiff = {
    val addedRolePlayers = rolePlayers -- rolePlayersBkp
    val removedRolePlayers = rolePlayersBkp -- rolePlayers
    val addedLiterals = literals -- literalsBkp
    val removedLiterals = literalsBkp -- literals
    val modifiedLiterals = Set[Literal]() //TODO unary literals and text diffs
    ZFieldDiff(addedRolePlayers, removedRolePlayers,
      addedLiterals, removedLiterals, modifiedLiterals)
  }

  //  Mutators

  protected def update( fieldSets: FieldSetMap[ZModifiedFieldSet] = fieldSets,
                        deleted_? : Boolean = false
                        ): ZModifiedField = {
    ZModifiedField( id, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: RolePlayerSet = rolePlayers,
                              literals: LiteralSet = literals
                              ): ZModifiedField = {
    ZModifiedField( id, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }

  def mutateRolePlayers(mutate: RolePlayerSet => RolePlayerSet): ZField =
    updateField( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer(role: ZRole, player: ZObject): ZField =
    mutateRolePlayers(_ + ((role, player)) )

  def removeRolePlayer(role: ZRole, player: ZObject): ZField =
    mutateRolePlayers( _ - ((role, player)) )

  def mutateLiterals(mutate: LiteralSet => LiteralSet): ZField =
    updateField( literals = mutate(literals) )

  def addLiteral(literal: Literal): ZField =
    mutateLiterals( _ + literal )

  def removeLiteral(literal: Literal): ZField =
    mutateLiterals( _ - literal )

  def applyDiff(diff: ZFieldDiff): ZModifiedField = {
    val newRolePlayers = rolePlayers ++ diff.addedRolePlayers -- diff.removedRolePlayers
    val newLiterals = literals ++ diff.addedLiterals -- diff.removedLiterals

    updateField( rolePlayers = newRolePlayers, literals = newLiterals )
  }

  def merge(other: ZPersistedField): ZModifiedField = {
    require(id == other.id, "must merge a modified and persisted version of the same field")
    val newFieldSets = fieldSets map {fs =>
      (fs._1 -> (fs._2 merge other.fieldSets(fs._1)))
    }
    other.applyDiff( calcDiff ).update( fieldSets = newFieldSets )
  }

  override def reload(limit: Int = 0): ZModifiedField = {
    val latest = DB.getUpdatedField(this, limit)
    this merge latest
  }

  // Persistence

  override def save = ???
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
case class
ZNewField protected[model] ( id: TempId,
                             zClass: ZFieldClass,
                             fieldSets: FieldSetMap[ZNewFieldSet],
                             rolePlayers: RolePlayerSet,
                             literals: LiteralSet,
                             deleted_? : Boolean = false
                             )
  extends ZNewObject
  with ZField with ZMutableField {
  type T = ZNewField

  protected def update( fieldSets: FieldSetMap[ZNewFieldSet] = fieldSets,
                        deleted_? : Boolean = false ): ZNewField = {
    ZNewField( id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: RolePlayerSet = rolePlayers,
                              literals: LiteralSet = literals
                              ): ZNewField = {
    ZNewField( id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }

  override def save = ???
}
