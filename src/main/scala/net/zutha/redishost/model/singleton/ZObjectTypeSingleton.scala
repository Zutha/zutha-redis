package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import scala.reflect.runtime.universe._

/**
 * This should be mixed in to the the Scala Companion Objects of ZClass Schema Items.
 * @tparam T an upper bound on the type of instances of the ZClass this Scala Object is the Companion of.
 * @tparam TI the immutable version of T
 * @tparam TM the mutable version of T
 */
protected[redishost] trait ZObjectTypeSingleton
[
  T <: ZObject,
  TI <: T with IObject,
  TM <: T with MObject
]
  extends ZTypeSingleton
  with ZItemSingleton[ZObjectType, IObjectType, MObjectType]
{

  def find( key: String )( implicit acc: ImmutableAccessor, tt: TypeTag[TI] ): Option[IRef[TI]] = {
    //    acc.getTypedRef[TI]( key )
    ???
  }

  def find( key: String )( implicit acc: MutableAccessor, tt: TypeTag[TM] ): Option[MRef[TM]] = {
    acc.getTypedRef[TM]( key )
  }

}
