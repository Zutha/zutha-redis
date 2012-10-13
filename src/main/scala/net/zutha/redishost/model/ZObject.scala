package net.zutha.redishost.model

import net.zutha.redishost.db.DB
import common._

object ZObject {

}


trait ZObject {

  // Accessors

  def id: ZIdentity
  def zClass: ZClass
  def fieldSets: Map[(ZRole, ZFieldClass), ZFieldSet]

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
  def fieldSets: Map[(ZRole, ZFieldClass), ZFieldSet] = reload().fieldSets

}



case class
ZPersistedObject protected[model] ( id: ZPersistedIdentity,
                                    zClass: ZClass,
                                    fieldSets: Map[(ZRole, ZFieldClass), ZPersistedFieldSet]
                                    ) extends ZObject {
  type C = ZClass

  def edit: ZModifiedObject =
    ZModifiedObject(id, zClass, zClass, fieldSets.mapValues(_.edit), deleted_? = false )
}



trait ZMutableObject extends ZObject {
  type T <: ZMutableObject
  type S <: ZMutableFieldSet

  // Accessors

  override def fieldSets: Map[(ZRole, ZFieldClass), S]
  def deleted_? : Boolean

  // Mutation

  protected
  def update( fieldSets: Map[(ZRole, ZFieldClass), S] = fieldSets,
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

  def save()
}


/**
 * A Persisted Object that possibly has unsaved modifications
 */
object ZModifiedObject {
  private[ZModifiedObject] case class
  ModifiedObject ( id: ZPersistedIdentity,
                   zClassBkp: ZClass,
                   zClass: ZClass,
                   fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet],
                   deleted_? : Boolean
                   )
    extends ZModifiedObject {
    type T = ZModifiedObject
    type C = ZClass

    protected def update( fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet],
                          deleted_? : Boolean ): ZModifiedObject = {
      ModifiedObject( id, zClassBkp, zClass, fieldSets, deleted_? )
    }

  }

  def apply( id: ZPersistedIdentity,
             zClassBkp: ZClass,
             zClass: ZClass,
             fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet],
             deleted_? : Boolean = false
             ) = ModifiedObject( id, zClassBkp, zClass, fieldSets, deleted_? )
}
trait ZModifiedObject extends ZObject with ZMutableObject {
  type T <: ZModifiedObject
  type S = ZModifiedFieldSet

  def id: ZPersistedIdentity
  def fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet]

  def save() {}

}


/**
 * An Object that has not been persisted to the database
 */
object ZNewObject {
  private[ZNewObject] case class
  NewObject ( id: TempId,
              zClass: ZClass,
              fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet],
              deleted_? : Boolean
              ) extends ZNewObject {
    type T = ZNewObject
    type C = ZClass

    protected def update( fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet],
                          deleted_? : Boolean ) = {
      NewObject( id, zClass, fieldSets, deleted_? )
    }

  }

  def apply( id: TempId,
             zClass: ZClass,
             fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet],
             deleted_? : Boolean = false
             ) = NewObject( id, zClass, fieldSets, deleted_? )
}
trait ZNewObject extends ZObject with ZMutableObject {
  type T <: ZNewObject
  type S = ZNewFieldSet

  def id: TempId
  def fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet]

  def save() {}
}



