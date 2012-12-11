package net.zutha.redishost.model.singleton

import net.zutha.redishost.db.{Accessor, MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaObjectMissingException
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model._
import literaltype.Name
import scala.reflect.runtime.universe._

object ZItemSingleton {
  import scala.language.implicitConversions
  implicit def schemaItemToRef[B <: ZObjT, T <: ZItem] ( si: ZItemSingleton[T] )
                                                       ( implicit acc: Accessor[B],
                                                         ttag: TypeTag[T] ): Ref[B, T] = {
    si.ref
  }
}

/**
 * mixin for scala objects that represent a specific Zutha Item
 * @tparam This the ZType(s) of this ZItem. If it has multiple ZTypes, they should all be mixed in as Scala traits.
 */
protected[redishost] trait ZItemSingleton[+This <: ZItem]
{
  type L = This

  /**
   * @return the name of this Schema Item. This name is globally unique across the Zuthanet
   */
  def name: String

  /**
   * @return a Ref to the ZItem that this Scala Object is the companion of
   */
  def ref[U <: ZObject, T >: L <: U]( implicit acc: Accessor[U],
                                      tt: TypeTag[T] ): Ref[U, T] = {
    val ref = acc.lookupObjectKeyByName( name ) match {
      case Some(key) => acc.getTypedRef( key ).get
      case None => acc match {
        case mAcc: MutableAccessor => Ref( Name.indexForm(name) )
        case iAcc: ImmutableAccessor => throw new SchemaObjectMissingException( name )
      }
    }
    ref
  }

}
