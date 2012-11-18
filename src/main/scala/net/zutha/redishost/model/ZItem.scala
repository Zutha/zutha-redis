package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.MsgType._

object ZItem extends ZItemClassCompanion[ZItem, IItem, MItem] {

  def name = "ZItem"

  def validType_?(obj: ZObject): Boolean = ???

  def apply( zClass: MRef[MItemClass]
             )( implicit acc: MutableAccessor ): NewItem = {
    acc.createItem( zClass )
  }
}


/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem
  extends ZObject
{
	type T <: ZItem

  def id: ZIdentity
  def zClass: ZRef[ZItemClass]
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

  override def zClass: IRef[IItemClass]

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

  override def zClass: MRef[MItemClass]

  protected def updateClass( zClass: MRef[MItemClass] ): T

}

/**
 * An immutable Item that corresponds to an Item in the database
 */
case class ImmutableItem protected[redishost] ( id: Zids,
                                                zClass: IRef[IItemClass],
                                                fieldSets: Seq[IFieldSetRef]
                                                )( implicit val acc: ImmutableAccessor )
  extends IItem
{
	type T <: IItem
}

/**
 * A Persisted Item that possibly has unsaved modifications
 */
case class ModifiedItem protected[redishost] ( id: PersistedId,
                                               zClassOrig: MRef[MItemClass],
                                               zClass: MRef[MItemClass],
                                               fieldSets: Seq[MFieldSetRef],
                                               messages: Seq[(MsgType, String)] = Seq(),
                                               deleted_? : Boolean = false
                                               )( implicit val acc: MutableAccessor )
  extends ModifiedObject
  with MItem
{
	type T <: ModifiedItem

  protected def update( fieldSets: Seq[MFieldSetRef],
                        deleted_? : Boolean ): T = {
    ModifiedItem( id, zClassOrig, zClass, fieldSets, messages, deleted_? ).asInstanceOf[ModifiedItem with T]
  }

  protected def updateClass(zClass: MRef[MItemClass]): T =
    ModifiedItem( id, zClassOrig, zClass, fieldSets, messages, deleted_? ).asInstanceOf[ModifiedItem with T]

//  def merge(other: ModifiedItem): ModifiedItem = {
//    //TODO cater for merging
//    require(id == other.id, "must merge a modified and persisted version of the same item")
//    if(zClassOrig == other.zClass){
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
 */
case class NewItem protected[redishost] ( id: TempId,
                                          zClass: MRef[MItemClass],
                                          fieldSets: Seq[MFieldSetRef],
                                          messages: Seq[(MsgType, String)] = Seq(),
                                          deleted_? : Boolean = false
                                          )( implicit val acc: MutableAccessor )
  extends NewObject
  with MItem
{
  type T <: NewItem

  protected def update( fieldSets: Seq[MFieldSetRef],
                        deleted_? : Boolean ): T = {
    NewItem( id, zClass, fieldSets, messages, deleted_? ).asInstanceOf[NewItem with T]
  }

  protected def updateClass(zClass: MRef[MItemClass]): T = {
    NewItem( id, zClass, fieldSets, messages, deleted_? ).asInstanceOf[NewItem with T]
  }


}
