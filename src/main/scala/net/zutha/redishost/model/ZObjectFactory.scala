package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait ZObjectFactory
[
  T <: ZObject,
  TI[A <: ImmutableAccessor] <: T with IObject[A],
  TM[A <: MutableAccessor] <: T with MObject[A]
]
{

  def typeName: String

  def validType_?(obj: ZObject): Boolean

  private def toT[A <: Accessor, C <: ZObjectT[T]]( acc: A,
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

  def apply[A <: ImmutableAccessor](acc: A, id: Zid): Option[IObjectT[A, TI]] = {
    val obj = acc.getObject(id)
    toT[A, IObjectT[A, TI]](acc, obj)
  }

  def apply[A <: MutableAccessor](acc: A, id: ZIdentity): Option[MObjectT[A, TM]] = {
    val obj = acc.getObject(id)
    toT[A, MObjectT[A, TM]](acc, obj)
  }

  def getI[A <: ImmutableAccessor](acc: A): TI[A] = {
    ??? //TODO lookup using typeName
  }

  def getA[A <: MutableAccessor](acc: A): TM[A] = {
    ??? //TODO lookup using typeName
  }
}
