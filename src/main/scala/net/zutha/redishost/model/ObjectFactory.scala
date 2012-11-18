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

  def validType_?(obj: ZObject): Boolean

  private def toT[C <: T]( acc: Accessor,
                           obj: Option[ZObject]
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

  def apply(acc: ImmutableAccessor, id: Zid): Option[TI] = {
    val obj = acc.getObject(id)
    toT[TI](acc, obj)
  }

  def apply(acc: MutableAccessor, id: ZIdentity): Option[TM] = {
    val obj = acc.getObject(id)
    toT[TM](acc, obj)
  }

}
