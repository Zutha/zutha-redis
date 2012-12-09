package net.zutha.redishost.model.companion

import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model._
import itemclass._

/**
 * This should be mixed in to the the Scala Companion Objects of ZClass Schema Items.
 * @tparam T an upper bound on the type of instances of the ZClass this Scala Object is the Companion of.
 * @tparam TI the immutable version of T
 * @tparam TM the mutable version of T
 */
protected[redishost] trait ZClassCompanion
[
  T <: ZObject,
  TI <: T with IObject,
  TM <: T with MObject
]
  extends SchemaItem
{
  type ObjC <: ZClass
  type ObjCI <: ObjC with IClass
  type ObjCM <: ObjC with MClass


  def validType_?( obj: ZObject ): Boolean = obj match {
    case o: IObject => o.hasType_?( this.refI(o.acc) )
    case o: MObject => o.hasType_?( this.refM(o.acc) )
  }

  protected def toT[C <: T]( obj: Option[ZObject]
                           ): Option[C] = {
    obj flatMap {o =>
      if( validType_?(o) )
        Some( o.asInstanceOf[C] )
      else {
        None
      }
    }
  }

  def apply( id: Zid )( implicit acc: ImmutableAccessor ): Option[TI] = {
    val obj = acc.getObject( id )
    toT[TI]( obj )
  }

  def apply( id: ZIdentity )( implicit acc: MutableAccessor ): Option[TM] = {
    val obj = acc.getObjectById( id )
    toT[TM]( obj )
  }

}
