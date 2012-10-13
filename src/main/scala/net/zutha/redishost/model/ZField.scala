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
             rolePlayers: (ZRole, ZObject)*
             ): ZNewField = {
    val id = new TempId
    val rps = rolePlayers.toSet
    val fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet] = Map() //TODO get from db (implement in ZObject)
    ZNewField( id, zClass, fieldSets, rps, Set() )
  }
}


trait ZField extends ZObject {

  def id: ZFieldIdentity
  def zClass: ZFieldClass
  def fieldSets: Map[(ZRole, ZFieldClass), ZFieldSet]
  def rolePlayers: Set[(ZRole, ZObject)]
  def literals: Set[ZLiteral]

  def rolePlayerMap: Map[ZRole, Set[ZObject]] =
    rolePlayers.groupBy(_._1).mapValues(_.map(_._2))

  def getPlayersOf(role: ZRole): Set[ZObject] =
    rolePlayers.filter(_._1 == role).map(_._2)

  def players: Set[ZObject] = rolePlayers.map(_._2)

  def literalsOfType(literalType: ZLiteralType): Set[ZLiteral] =
    literals.filter(_.literalType == literalType)

  def firstLiteralOfType(literalType: ZLiteralType): ZLiteral =
    literalsOfType(literalType).head
}




case class
ZPersistedField protected[model] ( override val id: SingleZid,
                                   override val zClass: ZFieldClass,
                                   override val fieldSets: Map[(ZRole, ZFieldClass), ZPersistedFieldSet],
                                   rolePlayers: Set[(ZRole, ZObject)],
                                   literals: Set[ZLiteral]
                                   )
  extends ZPersistedObject( id, zClass, fieldSets )
  with ZField {

  override def edit: ZModifiedField =
    ZModifiedField( id, zClass, zClass, fieldSets.mapValues(_.edit),
    rolePlayers, rolePlayers, literals, literals )

}

/**
 * ZMutableField
 * A Field that can be Modified
 */
trait ZMutableField extends ZField with ZMutableObject {
  type T <: ZMutableField

  def deleted_? : Boolean

  protected
  def updateField ( rolePlayers: Set[(ZRole, ZObject)] = rolePlayers,
                    literals: Set[ZLiteral] = literals
                    ): T

}

/**
 * ZModifiedField
 * A Persisted Field that possibly has unsaved modifications
 *
 * @param id
 * @param zClassBkp
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
                                  zClassBkp: ZFieldClass,
                                  zClass: ZFieldClass,
                                  fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet],
                                  rolePlayersBkp: Set[(ZRole, ZObject)],
                                  rolePlayers: Set[(ZRole, ZObject)],
                                  literalsBkp: Set[ZLiteral],
                                  literals: Set[ZLiteral],
                                  deleted_? : Boolean = false
                                  )
  extends ZModifiedObject with ZField with ZMutableField {
  type T = ZModifiedField

  // Accessors

  //  Mutators

  protected def update( fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet] = fieldSets,
                        deleted_? : Boolean = false
                        ): ZModifiedField = {
    ZModifiedField( id, zClassBkp, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: Set[(ZRole, ZObject)] = rolePlayers,
                              literals: Set[ZLiteral] = literals
                              ): ZModifiedField = {
    ZModifiedField( id, zClassBkp, zClass, fieldSets,
      rolePlayersBkp, rolePlayers, literalsBkp, literals, deleted_? )
  }

  def mutateRolePlayers(mutate: Set[(ZRole, ZObject)] => Set[(ZRole, ZObject)]): ZField =
    updateField( rolePlayers = mutate(rolePlayers) )

  def addRolePlayer(role: ZRole, player: ZObject): ZField =
    mutateRolePlayers(_ + ((role, player)) )

  def removeRolePlayer(role: ZRole, player: ZObject): ZField =
    mutateRolePlayers( _ - ((role, player)) )

  def mutateLiterals(mutate: Set[ZLiteral] => Set[ZLiteral]): ZField =
    updateField( literals = mutate(literals) )

  def addLiteral(literal: ZLiteral): ZField =
    mutateLiterals( _ + literal )

  def removeLiteral(literal: ZLiteral): ZField =
    mutateLiterals( _ - literal )

  override def reload(limit: Int = 0): ZPersistedField = {
    val latest = DB.getUpdatedField(this, limit)
    latest
  }

  // TODO implement stub
  override def merge(other: ZObject): ZField = other match {
    case f:ZField => f
    case _ => throw new IllegalArgumentException
  }

  // Persistence

  override def save() {}
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
                             fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet],
                             rolePlayers: Set[(ZRole, ZObject)],
                             literals: Set[ZLiteral],
                             deleted_? : Boolean = false
                             )
  extends ZNewObject
  with ZField with ZMutableField {
  type T = ZNewField

  protected def update( fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet] = fieldSets,
                        deleted_? : Boolean = false ): ZNewField = {
    ZNewField( id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }
  protected def updateField ( rolePlayers: Set[(ZRole, ZObject)] = rolePlayers,
                              literals: Set[ZLiteral] = literals
                              ): ZNewField = {
    ZNewField( id, zClass, fieldSets, rolePlayers, literals, deleted_? )
  }
}