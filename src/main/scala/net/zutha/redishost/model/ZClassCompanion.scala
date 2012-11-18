package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}

protected[redishost] trait ZClassCompanion
[
  T <: ZObject,
  TI <: T with IObject,
  TM <: T with MObject
]
  extends SchemaItem
{

  def validType_?( obj: ZObject ): Boolean

  protected def toT[C <: T]( obj: Option[ZObject]
                           ): Option[C] = {
    obj flatMap {o =>
      if(validType_?(o) )
        Some(o.asInstanceOf[C])
      else {
        None
        // throw new Exception( s"id $id does not refer to an object of type $typeName" )
      }
    }
  }

  def apply( id: Zid )( implicit acc: ImmutableAccessor ): Option[TI] = {
    val obj = acc.getObject(id)
    toT[TI](obj)
  }

  def apply( id: ZIdentity )( implicit acc: MutableAccessor ): Option[TM] = {
    val obj = acc.getObject(id)
    toT[TM](obj)
  }

}
