package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[redishost] trait ObjectFactory
[
  T <: ZObject,
  TI <: T with IObject,
  TM <: T with MObject
]
  extends SchemaObject
{

  def validType_?( obj: ZObject ): Boolean

  private def toT[C <: T]( obj: Option[ZObject]
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
