package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait ZObjectFactory
[
  T <: ZObject,
  TI <: T with IObject,
  TM <: T with MObject
]
{

  def typeName: String

  def validType_?(obj: ZObject): Boolean

  private def toT[C <: ZObjectT[T]]( acc: Accessor,
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

  def apply(acc: ImmutableAccessor, id: Zid): Option[IObjectT[TI]] = {
    val obj = acc.getObject(id)
    toT[IObjectT[TI]](acc, obj)
  }

  def apply(acc: MutableAccessor, id: ZIdentity): Option[MObjectT[TM]] = {
    val obj = acc.getObject(id)
    toT[MObjectT[TM]](acc, obj)
  }

  def getI(acc: ImmutableAccessor): TI = {
    ??? //TODO lookup using typeName
  }

  def getA(acc: MutableAccessor): TM = {
    ??? //TODO lookup using typeName
  }
}
