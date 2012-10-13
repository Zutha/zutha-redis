package net.zutha.redishost.model

import common._
import net.zutha.redishost.db.DB

/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem extends ZObject {

  def id: ZItemIdentity
  def zClass: ZItemClass

}


/**
 * An immutable Item that corresponds to an Item in the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 */
case class
ZPersistedItem protected[model] ( id: Zids,
                                  zClass: ZItemClass,
                                  fieldSets: FieldSetMap[ZPersistedFieldSet]
                                  ) extends ZPersistedObject with ZItem {

  def edit: ZModifiedItem =
    ZModifiedItem(id, zClass, zClass, fieldSets.mapValues(_.edit), deleted_? = false )

  def merge(other: ZModifiedItem): ZModifiedItem = {
    other merge this
  }

  /** reloads the object from the database
    * @param limit the maximum number of fields to load per field set
    */
  def reload(limit: Int): ZPersistedItem = {
    val latest = DB.getUpdatedItem(this, limit)
    latest
  }
}

/**
 * An Item that can be Modified
 */
trait ZMutableItem extends ZItem with ZMutableObject {
  type T <: ZMutableItem

  protected def updateClass( zClass: ZItemClass ): T

  override def save: Option[ZPersistedItem]
}

/**
 * A Persisted Item that possibly has unsaved modifications
 *
 * @param id
 * @param zClassBkp
 * @param zClass
 * @param fieldSets
 * @param deleted_?
 */
case class
ZModifiedItem protected[model] ( id: Zids,
                                 zClassBkp: ZItemClass,
                                 zClass: ZItemClass,
                                 fieldSets: FieldSetMap[ZModifiedFieldSet],
                                 deleted_? : Boolean
                                 ) extends ZModifiedObject with ZItem with ZMutableItem {
  type T = ZModifiedItem

  protected def update( fieldSets: FieldSetMap[ZModifiedFieldSet],
                        deleted_? : Boolean ): ZModifiedItem = {
    ZModifiedItem( id, zClassBkp, zClass, fieldSets, deleted_? )
  }

  protected def updateClass(zClass: ZItemClass) = ZModifiedItem( id, zClassBkp, zClass, fieldSets, deleted_? )

  def merge(other: ZPersistedItem): ZModifiedItem = {
    //TODO cater for merging
    require(id == other.id, "must merge a modified and persisted version of the same item")
    if(zClassBkp == other.zClass){
      val newFieldSets = fieldSets map {fs => other.fieldSets.get(fs._1) match {
        case Some(ofs) => (fs._1 -> (fs._2 merge ofs))
        case None => fs
      }}
      val merged = other.edit.update( fieldSets = newFieldSets )
      if (zClass == other.zClass) merged else merged.updateClass(zClass)
    } else { // conflict in class - abandon this edit
      throw new Exception("persisted item's class changed since modification was started")
    }
  }

  // Persistence

  /** reloads the object from the database
    * @param limit the maximum number if fields to load per field set
    */
  def reload(limit: Int): ZModifiedItem = {
    val latest = DB.getUpdatedItem(this, limit)
    this merge latest
  }

  override def save = ???
}

/**
 * An Item that has not been persisted to the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 * @param deleted_?
 */
case class
ZNewItem protected[model] ( id: TempId,
                            zClass: ZItemClass,
                            fieldSets: FieldSetMap[ZNewFieldSet],
                            deleted_? : Boolean
                            ) extends ZNewObject with ZItem with ZMutableItem {
  type T = ZNewItem

  protected def update( fieldSets: FieldSetMap[ZNewFieldSet],
                        deleted_? : Boolean ): ZNewItem = {
    ZNewItem( id, zClass, fieldSets, deleted_? )
  }

  protected def updateClass(zClass: ZItemClass) = ZNewItem( id, zClass, fieldSets, deleted_? )

  // Persistence

  override def save = ???

}
