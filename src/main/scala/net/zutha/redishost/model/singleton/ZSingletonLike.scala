package net.zutha.redishost.model.singleton

import net.zutha.redishost.model.special.ZNothing
import net.zutha.redishost.model.itemclass.{ZObject, ZItem}
import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor, Accessor}
import net.zutha.redishost.model._
import literaltype.Name
import scala.reflect.runtime.universe._
import net.zutha.redishost.exception.SchemaObjectMissingException

trait ZSingletonLike[+This >: ZNothing <: ZItem]
{
  self: ZSingleton[This] =>

  type L = This

  /**
   * @return the name of this Schema Item. This name is globally unique across the Zuthanet
   */
  def name: String

  def zKey[A <: Accessor[A] ]( implicit acc: A ): String = {
    val key = acc.lookupObjectKeyByName( name ) match {
      case Some(k) => k
      case None => acc match {
        case mAcc: MutableAccessor => Name.indexForm(name)
        case iAcc: ImmutableAccessor => throw new SchemaObjectMissingException( name )
      }
    }
    key
  }

  /**
   * @return a ZRef to the ZItem that this Scala Object is the companion of
   */
  def ref[A <: Accessor[A], T >: L <: ZObject]( implicit acc: A,
                                                tt: TypeTag[T] ): ZRef[A, T] = {

    ZRef[A, T]( zKey[A] )
  }

  def refI[T >: L <: ZObject]( implicit acc: IA,
                               tt: TypeTag[T] ): IRef[T] = {
    ref[IA, T]
  }

  def refM[T >: L <: ZObject]( implicit acc: MA,
                               tt: TypeTag[T] ): MRef[T] = {
    ref[MA, T]
  }

}
