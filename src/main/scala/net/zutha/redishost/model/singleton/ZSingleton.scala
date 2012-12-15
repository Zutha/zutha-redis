package net.zutha.redishost.model.singleton

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model._
import scala.reflect.runtime.universe._
import special.ZNothing

object ZSingleton {
  import scala.language.implicitConversions

  implicit def zSingletonToIRef[T >: ZNothing <: ZItem] ( si: ZSingleton[T] )
                                                        ( implicit acc: ImmutableAccessor,
                                                          tt: TypeTag[T] ): IRef[T] = {
    si.refI
  }

  implicit def zSingletonToMRef[T >: ZNothing <: ZItem] ( si: ZSingleton[T] )
                                                        ( implicit acc: MutableAccessor,
                                                          tt: TypeTag[T] ): MRef[T] = {
    si.refM
  }
}

/**
 * the class of scala objects that represent a specific Zutha Item
 * @tparam This the ZObjectType(s) of this ZItem. If it has multiple ZTypes, they should all be mixed in as Scala traits.
 */
protected[redishost] abstract class ZSingleton[+This >: ZNothing <: ZItem: TypeTag]
  extends ZSingletonLike[This]
{

}
