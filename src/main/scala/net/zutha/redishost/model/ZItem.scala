package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZItem extends ZObjectFactory[ZItem, IItem, MItem] {
  def typeName = "ZItem"

  def validType_?(obj: ZObject): Boolean = ???
}


/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem
  extends ZObject
{
	type T <: ZItem

  def id: ZIdentity
  def zClass: ZItemClass
}

/**
 * An Item that cannot be Modified
 */
trait IItem
  extends IObject
  with ZItem
{
	type T <: IItem

  override def id: Zids

  override def zClass: IItemClass

}

/**
 * An Item that can be Modified
 */
trait MItem
  extends MObject
  with ZItem
{
	type T <: MItem

  override def id: ZIdentity

  override def zClass: MItemClass

  protected def updateClass( zClass: MItemClass ): T

}

/**
 * An immutable Item that corresponds to an Item in the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 */
case class ImmutableItem protected[redishost] ( acc: ImmutableAccessor,
                                                id: Zids,
                                                zClass: IRefT[IItemClass],
                                                fieldSets: IFieldSetMap
                                                )
  extends IItem
{
	type T <: IItem

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
case class ModifiedItem protected[redishost] ( acc: MutableAccessor,
                                               id: PersistedId,
                                               zClassBkp: MRefT[MItemClass],
                                               zClass: MRefT[MItemClass],
                                               fieldSets: MFieldSetMap,
                                               deleted_? : Boolean
                                               )
  extends ModifiedObject
  with MItem
{
	type T <: ModifiedItem

  protected def update( fieldSets: MFieldSetMap,
                        deleted_? : Boolean ): T = {
    ModifiedItem( acc, id, zClassBkp, zClass, fieldSets, deleted_? ).asInstanceOf[ModifiedItem with T]
  }

  protected def updateClass(zClass: MItemClass): T =
    ModifiedItem( acc, id, zClassBkp, zClass.ref, fieldSets, deleted_? ).asInstanceOf[ModifiedItem with T]

//  def merge(other: ModifiedItem): ModifiedItem = {
//    //TODO cater for merging
//    require(id == other.id, "must merge a modified and persisted version of the same item")
//    if(zClassBkp == other.zClass){
//      val newFieldSets = fieldSets map {fs => other.fieldSets.get(fs._1) match {
//        case Some(ofs) => (fs._1 -> (fs._2 merge ofs))
//        case None => fs
//      }}
//      val merged = other.update( fieldSets = newFieldSets )
//      if (zClass == other.zClass) merged else merged.updateClass(zClass)
//    } else { // conflict in class - abandon this edit
//      throw new Exception("persisted item's class changed since modification was started")
//    }
//  }

}

/**
 * An Item that has not been persisted to the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 * @param deleted_?
 */
case class NewItem protected[redishost] ( acc: MutableAccessor,
                                          id: TempId,
                                          zClass: MRefT[MItemClass],
                                          fieldSets: MFieldSetMap,
                                          deleted_? : Boolean
                                          )
  extends NewObject
  with MItem
{
  type T <: NewItem

  protected def update( fieldSets: MFieldSetMap,
                        deleted_? : Boolean ): T = {
    NewItem( acc, id, zClass, fieldSets, deleted_? ).asInstanceOf[NewItem with T]
  }

  protected def updateClass(zClass: MItemClass): T = {
    NewItem( acc, id, zClass.ref, fieldSets, deleted_? ).asInstanceOf[NewItem with T]
  }


}
