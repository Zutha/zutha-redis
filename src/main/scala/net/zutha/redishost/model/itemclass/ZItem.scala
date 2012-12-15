package net.zutha.redishost.model.itemclass

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model._
import fieldset._
import MsgType._
import singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}

object ZItem
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZItem]
  with ZRoleSingleton
{
  def name = "Item"

  def apply( zClass: MRef[ZItemClass] )
           (implicit acc: MutableAccessor ): NewItem = {
    acc.createItem( zClass )
  }
}

/**
 * Represents some concept or thing. May contain fields which associate it with data or other items.
 */
trait ZItem
  extends ZObject
  with Referenceable[ZItem]

/**
 * An immutable Item
 */
trait IItem
  extends ZItem
  with IObject
  with IItemLike


/**
 * A concrete and Accessible Immutable Item corresponding to an Item in the database
 */
case class ImmutableItem protected[redishost] ( id: Zids,
                                                zClass: IRef[ZItemClass],
                                                fieldSets: Seq[IFieldSetRef] )
                                              ( implicit val acc: IA )
  extends IItem
  with Loadable[ImmutableItem, ZItem]


/**
 * A Mutable Item
 */
trait MItem
  extends ZItem
  with MObject
  with MItemLike


/**
 * A Persisted Item that possibly has unsaved modifications
 */
case class ModifiedItem protected[redishost] ( id: PersistedId,
                                               zClassOrig: MRef[ZItemClass],
                                               zClass: MRef[ZItemClass],
                                               fieldSets: Seq[MFieldSetRef],
                                               messages: Seq[(MsgType, String)] = Seq(),
                                               deleted_? : Boolean = false )
                                             ( implicit val acc: MA )
  extends MItem
  with Loadable[ModifiedItem, ZItem]


/**
 * An Item that has not been persisted to the database
 */
case class NewItem protected[redishost] ( id: TempId,
                                          zClass: MRef[ZItemClass],
                                          fieldSets: Seq[MFieldSetRef],
                                          messages: Seq[(MsgType, String)] = Seq(),
                                          deleted_? : Boolean = false )
                                        ( implicit val acc: MA )
  extends MItem
  with Loadable[NewItem, ZItem]