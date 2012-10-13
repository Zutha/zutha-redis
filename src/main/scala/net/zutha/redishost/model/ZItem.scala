package net.zutha.redishost.model

import common._

/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem extends ZObject {

  def id: ZItemIdentity
  def zClass: ZItemClass

}


/**
 * An immutable Item that corresponds to an Item in the database
 * @param id
 * @param zClass
 * @param fieldSets
 */
case class
ZPersistedItem protected[model] ( id: Zids,
                                  zClass: ZItemClass,
                                  fieldSets: Map[(ZRole, ZFieldClass), ZPersistedFieldSet]
                                  ) extends ZPersistedObject with ZItem {

  def edit: ZModifiedItem =
    ZModifiedItem(id, zClass, zClass, fieldSets.mapValues(_.edit), deleted_? = false )
}

/**
 * A Item that can be Modified
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
                                 fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet],
                                 deleted_? : Boolean
                                 ) extends ZModifiedObject with ZItem with ZMutableItem {
  type T = ZModifiedItem

  protected def update( fieldSets: Map[(ZRole, ZFieldClass), ZModifiedFieldSet],
                        deleted_? : Boolean ): ZModifiedItem = {
    ZModifiedItem( id, zClassBkp, zClass, fieldSets, deleted_? )
  }

  protected def updateClass(zClass: ZItemClass) = ZModifiedItem( id, zClassBkp, zClass, fieldSets, deleted_? )

  // Persistence

  override def save = ???
}

/**
 * An Item that has not been persisted to the database
 * @param id
 * @param zClass
 * @param fieldSets
 * @param deleted_?
 */
case class
ZNewItem protected[model] ( id: TempId,
                            zClass: ZItemClass,
                            fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet],
                            deleted_? : Boolean
                            ) extends ZNewObject with ZItem with ZMutableItem {
  type T = ZNewItem

  protected def update( fieldSets: Map[(ZRole, ZFieldClass), ZNewFieldSet],
                        deleted_? : Boolean ): ZNewItem = {
    ZNewItem( id, zClass, fieldSets, deleted_? )
  }

  protected def updateClass(zClass: ZItemClass) = ZNewItem( id, zClass, fieldSets, deleted_? )

  // Persistence

  override def save = ???

}
