package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._
import net.zutha.redishost.db.Accessor
import scala.reflect.runtime.universe._

/**
 * This should be mixed in to the the Scala Companion Objects of ZClass Schema Items.
 * @tparam T an upper bound on the type of instances of the ZClass this Scala Object is the Companion of.
 */
protected[redishost] trait
ZObjectTypeSingleton[+T <: ZObject]
  extends ZTypeSingleton
  with ZItemSingleton[ZObjectType]
{

  def find[U <: ZObject]( key: String )( implicit acc: Accessor[U], tt: TypeTag[T] ): Option[Ref[U, T]] = {
    acc.getTypedRef[T]( key )
  }

}
