package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZItem extends ZObjectFactory[ZItem, ZIItem, ZMItem] {
  def typeName = "ZItem"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}


/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem
  extends ZObject
  with HasRef[ZItem] {

  def id: ZIdentity
  def zClass: ZItemClass
}

trait ZIItem[A <: ImmutableAccessor]
  extends ZItem
  with ZImmutableObject[A]
  with HasImmutableRef[A, ZIItem[A]]

trait ZMItem[A <: MutableAccessor]
  extends ZItem
  with ZMutableObject[A]
  with HasMutableRef[A, ZMItem[A]]


/**
 * An immutable Item that corresponds to an Item in the database
 *
 * @param id
 * @param zClass
 * @param fieldSets
 */
case class ZImmutableItem
[A <: ImmutableAccessor] protected[redishost] ( acc: A,
                                                 id: Zid,
                                                 zClass: IReferenceT[A, ZIItemClass],
                                                 fieldSets: IFieldSetMap[A]
                                                 )
  extends ZConcreteImmutableObject[A]
  with ZItem
  with HasImmutableRef[A, ZImmutableItem[A]] {

}

/**
 * An Item that can be Modified
 */
trait ZMutableItem[A <: MutableAccessor]
  extends ZConcreteMutableObject[A]
  with ZItem
  with HasMutableRef[A, ZMutableItem[A]]
{
  override type T <: ZMutableItem[A]

  override def zClass: ZMItemClass[A]

  protected def updateClass( zClass: ZMItemClass[A] ): T

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
case class ZModifiedItem
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                               id: Zids,
                                               zClassBkp: MReferenceT[A, ZMItemClass],
                                               zClass: MReferenceT[A, ZMItemClass],
                                               fieldSets: MFieldSetMap[A],
                                               deleted_? : Boolean
                                               )
  extends ZModifiedObject[A]
  with ZMutableItem[A]
  with HasMutableRef[A, ZModifiedItem[A]]
{
  override type T = ZModifiedItem[A]

  protected def update( fieldSets: MFieldSetMap[A],
                        deleted_? : Boolean ): ZModifiedItem[A] = {
    ZModifiedItem( acc, id, zClassBkp, zClass, fieldSets, deleted_? )
  }

  protected def updateClass(zClass: ZMItemClass[A]) =
    ZModifiedItem( acc, id, zClassBkp, zClass.ref, fieldSets, deleted_? )

//  def merge(other: ZModifiedItem[A]): ZModifiedItem[A] = {
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
case class ZNewItem
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                               id: TempId,
                                               zClass: MReferenceT[A, ZMItemClass],
                                               fieldSets: MFieldSetMap[A],
                                               deleted_? : Boolean
                                               )
  extends ZNewObject[A]
  with ZMutableItem[A]
  with HasMutableRef[A, ZNewItem[A]]
{
  override type T = ZNewItem[A]

  protected def update( fieldSets: MFieldSetMap[A],
                        deleted_? : Boolean ): ZNewItem[A] = {
    ZNewItem( acc, id, zClass, fieldSets, deleted_? )
  }

  protected def updateClass(zClass: ZMItemClass[A]) = ZNewItem( acc, id, zClass.ref, fieldSets, deleted_? )


}
