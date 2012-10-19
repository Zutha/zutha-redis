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
trait IItem[A <: ImmutableAccessor]
  extends IObject[A]
  with ZItem
{
	type T <: IItem[A]

  override def id: Zid

  override def zClass: IItemClass[A]

}

/**
 * An Item that can be Modified
 */
trait MItem[A <: MutableAccessor]
  extends MObject[A]
  with ZItem
{
	type T <: MItem[A]

  override def zClass: MItemClass[A]

  protected def updateClass( zClass: MItemClass[A] ): T

}

/**
 * An immutable Item that corresponds to an Item in the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 */
case class ImmutableItem
[A <: ImmutableAccessor] protected[redishost] ( acc: A,
                                                id: Zid,
                                                zClass: IRefT[A, IItemClass],
                                                fieldSets: IFieldSetMap[A]
                                                )
  extends IItem[A]
{
	type T <: IItem[A]

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
case class ModifiedItem
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                               id: Zids,
                                               zClassBkp: MRefT[A, MItemClass],
                                               zClass: MRefT[A, MItemClass],
                                               fieldSets: MFieldSetMap[A],
                                               deleted_? : Boolean
                                               )
  extends ModifiedObject[A]
  with MItem[A]
{
	type T <: ModifiedItem[A]

  protected def update( fieldSets: MFieldSetMap[A],
                        deleted_? : Boolean ): T = {
    ModifiedItem( acc, id, zClassBkp, zClass, fieldSets, deleted_? ).asInstanceOf[ModifiedItem[A] with T]
  }

  protected def updateClass(zClass: MItemClass[A]): T =
    ModifiedItem( acc, id, zClassBkp, zClass.ref, fieldSets, deleted_? ).asInstanceOf[ModifiedItem[A] with T]

//  def merge(other: ModifiedItem[A]): ModifiedItem[A] = {
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
case class NewItem
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                               id: TempId,
                                               zClass: MRefT[A, MItemClass],
                                               fieldSets: MFieldSetMap[A],
                                               deleted_? : Boolean
                                               )
  extends NewObject[A]
  with MItem[A]
{
	type T <: NewItem[A]

  protected def update( fieldSets: MFieldSetMap[A],
                        deleted_? : Boolean ): T = {
    NewItem( acc, id, zClass, fieldSets, deleted_? ).asInstanceOf[NewItem[A] with T]
  }

  protected def updateClass(zClass: MItemClass[A]): T = {
    NewItem( acc, id, zClass.ref, fieldSets, deleted_? ).asInstanceOf[NewItem[A] with T]
  }


}
