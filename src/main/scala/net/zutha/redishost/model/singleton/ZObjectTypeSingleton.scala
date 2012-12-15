package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._
import net.zutha.redishost.db.Accessor
import scala.reflect.runtime.universe._
import special.ZNothing

/**
 * This should be mixed in to the the Scala Companion Objects of ZClass Schema Items.
 * @tparam T an upper bound on the type of instances of the ZClass this Scala Object is the Companion of.
 */
protected[redishost] trait
ZObjectTypeSingleton[+T >: ZNothing <: ZObject]
  extends ZTypeSingleton
  with ZSingletonLike[ZObjectType]
{
  self: ZSingleton[ZObjectType] =>

  def find[A <: Accessor[A], U >: T <: ZObject]( key: String )( implicit acc: A, tt: TypeTag[U] ): Option[ZRef[A, U]] = {
    acc.getTypedRef[U]( key )
  }

}
