package net.zutha.redishost.model.itemclass

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model._
import fieldset._
import MsgType._
import singleton.ZItemClassSingleton

object ZItem extends ZItemClassSingleton[ZItem, IItem, MItem] {

  def name = "Item"

  def apply( zClass: MRef[ZItemClass]
             )( implicit acc: MutableAccessor ): NewItem = {
    acc.createItem( zClass )
  }
}


/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem
  extends ZObject
  with ZItemLike[ZItem]

/**
 * An immutable Item that corresponds to an Item in the database
 */
case class ImmutableItem protected[redishost] ( id: Zids,
                                                zClass: IRef[ZItemClass],
                                                fieldSets: Seq[IFieldSetRef]
                                                )( implicit val acc: ImmutableAccessor )
  extends ZItem
  with IItemLike[ImmutableItem]
{

}

/**
 * A Persisted Item that possibly has unsaved modifications
 */
case class ModifiedItem protected[redishost] ( id: PersistedId,
                                               zClassOrig: MRef[ZItemClass],
                                               zClass: MRef[ZItemClass],
                                               fieldSets: Seq[MFieldSetRef],
                                               messages: Seq[(MsgType, String)] = Seq(),
                                               deleted_? : Boolean = false
                                               )( implicit val acc: MutableAccessor )
  extends ZItem
  with MItemLike[ModifiedItem]
{

}

/**
 * An Item that has not been persisted to the database
 */
case class NewItem protected[redishost] ( id: TempId,
                                          zClass: MRef[ZItemClass],
                                          fieldSets: Seq[MFieldSetRef],
                                          messages: Seq[(MsgType, String)] = Seq(),
                                          deleted_? : Boolean = false
                                          )( implicit val acc: MutableAccessor )
  extends ZItem
  with MItemLike[NewItem]
{

}
