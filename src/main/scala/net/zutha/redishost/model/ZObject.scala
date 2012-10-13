package net.zutha.redishost.model

import net.zutha.redishost.db.DB
import common._

object ZObject {

}


trait ZObject {

  // Accessors

  def id: ZIdentity
  def zClass: ZClass
  def fieldSets: FieldSetMap[ZFieldSet]

  def reference: ZObjectReference = ZObjectReference( id, zClass )

  def zids: Set[Zid] = id match {
    case TempId(_) => Set()
    case Zids(_, allZids) => allZids
    case SingleZid(zid) => Set(zid)
  }
  def primaryZids: Set[Zid] = id match {
    case TempId(_) => Set()
    case Zids(pZids, _) => pZids
    case SingleZid(zid) => Set(zid)
  }
  def zid: Option[Zid] = primaryZids.toSeq.sorted.headOption

  def persisted_? = primaryZids.size > 0

  def merged_? = primaryZids.size >= 2


  /** reloads the object from the database
    * @param limit the maximum number if fields to load per field set
    */
  def reload(limit: Int = 5): ZPersistedObject = {
    val latest = DB.getUpdatedObject(this, limit)
    latest
  }

  // Comparison

  def sameAs(other: ZObject): Boolean = id == other.id

}


case class
ZObjectReference protected[model] ( id: ZIdentity,
                                    zClass: ZClass
                                    ) extends ZObject {

  override def reference: ZObjectReference = this
  def fieldSets: FieldSetMap[ZFieldSet] = reload().fieldSets

}

/**
 * An immutable Object that corresponds to an Object in the database
 */
trait ZPersistedObject extends ZObject {
  def edit: ZModifiedObject
}


/**
 * An Object that can be Modified
 */
trait ZMutableObject extends ZObject {
  type T <: ZMutableObject
  type S <: ZMutableFieldSet

  // Accessors

  override def fieldSets: FieldSetMap[S]
  def deleted_? : Boolean

  // Mutation

  protected
  def update( fieldSets: FieldSetMap[S] = fieldSets,
              deleted_? : Boolean = deleted_?
              ): T

  def mutateFieldSets(mutate: S => S): T = {
    val newFieldSets = fieldSets.mapValues(mutate)
    update(fieldSets = newFieldSets)
  }
  def mutateFieldSet( role: ZRole, fieldClass: ZFieldClass )
                    ( mutate: S => S ): T = {
    val key = (role -> fieldClass)
    fieldSets.get(key) match {
      case Some(fieldSet) => update(fieldSets = fieldSets.updated(role -> fieldClass, mutate(fieldSet)))
      case None => throw new IllegalArgumentException(
        "object does not contain the specified fieldSet"
      )
    }
  }

  def delete: T = update( deleted_? = true )
  def restore: T = update( deleted_? = false )

  // TODO implement stub
  /**
   * Merges two objects. <br />
   *
   * If other is a modified version of this,
   * its modifications are applied to this object (which must be persisted).
   * If both objects are persisted, their fields are merged
   *
   * @param other the object to merge into this one
   * @return the merged object
   */
  def merge(other: ZObject): ZObject = ???


  // Persistence

  def save: Option[ZPersistedObject]
}


/**
 * A Persisted Object that possibly has unsaved modifications
 */
trait ZModifiedObject extends ZObject with ZMutableObject {
  type T <: ZModifiedObject
  type S = ZModifiedFieldSet

  def id: ZPersistedIdentity
  def fieldSets: FieldSetMap[ZModifiedFieldSet]

}


/**
 * An Object that has not been persisted to the database
 */
trait ZNewObject extends ZObject with ZMutableObject {
  type T <: ZNewObject
  type S = ZNewFieldSet

  def id: TempId
  def fieldSets: FieldSetMap[ZNewFieldSet]

}



