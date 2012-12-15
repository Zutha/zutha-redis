package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import singleton.{ZRoleSingleton, ZSingleton, ZClassSingleton}

object ZObject
  extends ZSingleton[ZClass with ZRole]
  with ZClassSingleton[ZObject]
  with ZRoleSingleton
{

  def name = "Object"

}

/**
 * Represents an referenceable construct in the Zuthanet
 */
trait ZObject
  extends Referenceable[ZObject]
{
  def id: ZIdentity

  def zKey: String
}

/**
 * An immutable ZObject
 */
trait IObject
  extends ZObject
  with IObjectLike


/**
 * A Mutable SObject
 */
trait MObject
  extends ZObject
  with MObjectLike

