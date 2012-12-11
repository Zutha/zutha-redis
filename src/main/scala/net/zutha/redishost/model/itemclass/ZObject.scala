package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZItemSingleton, ZClassSingleton}

object ZObject
  extends ZClassSingleton[ZObject, IObject, MObject]
  with ZItemSingleton[
    ZClass with ZRole,
    IClass with IRole,
    MClass with MRole ]
{

  def name = "Object"

}


trait ZObject extends ZObjectLike[ZObject]

trait PersistedObject
  extends ZObject
  with ZObjectLike[PersistedObject]
{
  override def id: PersistedId
}


/**
 * An immutable Object that corresponds to an Object in the database
 */
trait IObject
  extends PersistedObject
  with IObjectLike[IObject]

/**
 * An Object that can be Modified
 */
trait MObject
  extends ZObject
  with MObjectLike[MObject]


/**
 * A Persisted Object that possibly has unsaved modifications
 */
trait ModifiedObject
  extends MObject
  with MObjectLike[ModifiedObject]
  with PersistedObject
{
  def id: PersistedId
}


/**
 * An Object that has not been persisted to the database
 */
trait NewObject
  extends MObject
{
  def id: TempId

}



