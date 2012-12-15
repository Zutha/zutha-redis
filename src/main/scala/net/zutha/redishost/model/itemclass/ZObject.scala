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

/**
 * An immutable ZObject
 */
trait IObject
  extends ZObject
  with IObjectLike
  with Loadable[IObject, ZObject]


/**
 * A Mutable SObject
 */
trait MObject
  extends ZObject
  with MObjectLike
  with Loadable[MObject, ZObject]


trait ModifiedObject
  extends MObject
  with Loadable[ModifiedObject, ZObject]
{
  require( primaryZids.size >= 1 )
  require( primaryZids.forall( allZids.contains(_) ) )

  def key: String = primaryZids.head.key
}

trait NewObject
  extends MObject
  with Loadable[NewObject, ZObject]
{
  def primaryZids = Seq()
  def allZids = Seq()
}