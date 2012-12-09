package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import companion.ZClassCompanion

object ZObject extends ZClassCompanion[ZObject, IObject, MObject] {
  type ObjC = ZClass
  type ObjCI = IClass
  type ObjCM = MClass

  type ObjT = ZClass with ZRole
  type ObjTM = MClass with MRole
  type ObjTI = IClass with IRole

  protected def classFactory = ZClass

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



