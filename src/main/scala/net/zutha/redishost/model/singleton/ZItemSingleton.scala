package net.zutha.redishost.model.singleton

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaObjectMissingException
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model._
import literaltype.Name
import scala.reflect.runtime.universe._

object ZItemSingleton {
  import scala.language.implicitConversions
  implicit def schemaItemToRefM[T <: MItem]( si: ZItemSingleton[_, _, T] )
                                           ( implicit acc: MutableAccessor, ttag: TypeTag[T] ): MRef[T] = {
    si.refM(acc)
  }

  implicit def schemaItemToRefI[T <: IItem]( si: ZItemSingleton[_, T, _] )
                                           ( implicit acc: ImmutableAccessor, ttag: TypeTag[T] ): IRef[T] = {
    si.refI
  }
}

/**
 * mixin for scala objects that represent a specific Zutha Item
 * @tparam This the ZType(s) of this ZItem. If it has multiple ZTypes, they should all be mixed in as Scala traits.
 * @tparam ThisI the immutable version of This
 * @tparam ThisM the mutable version of This
 */
protected[redishost] trait ZItemSingleton
[
  +This <: ZItem,
  +ThisI <: This with IItem,
  +ThisM <: This with MItem
]
{
  type U = ZObject
  type UI = IObject
  type UM = MObject

  type L = This
  type LI = ThisI
  type LM = ThisM

  /**
   * @return the name of this Schema Item. This name is globally unique across the Zuthanet
   */
  def name: String

  /**
   * @return an IRef to the ZItem that this Scala Object is the companion of
   */
  def refI[T >: LI <: UI]( implicit acc: ImmutableAccessor, tt: TypeTag[T] ): IRef[T] = {
    val ref = acc.lookupObjectKeyByName( name ) match {
      case Some(key) => acc.getObjectRef(key).get.asInstanceOf[IRef[LI]]
      case None => throw new SchemaObjectMissingException( name )
    }
    ref
  }

  /**
   * @return an MRef to the ZItem that this Scala Object is the companion of
   */
  def refM[T >: LM <: UM]( implicit acc: MutableAccessor, tt: TypeTag[T] ): MRef[T] = {

    val ref = acc.lookupObjectKeyByName(name) match {
      case Some( key ) => acc.getTypedRef( key ).get
      case None => MRef( TempId( Name.indexForm(name) ))
    }
    ref
  }
}
