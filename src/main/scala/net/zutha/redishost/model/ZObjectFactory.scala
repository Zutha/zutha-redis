package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait ZObjectFactory
[
  T <: ZObject,
  TI[A <: ImmutableAccessor] <: T with ZImmutableObject[A],
  TM[A <: MutableAccessor] <: T with ZMutableObject[A]
]
{

  def typeName: String

  def validType_?(obj: ZConcreteObject): Boolean

  private def toT[A <: Accessor, C <: CObjectT[T]]( acc: A,
                                                    obj: Option[ZConcreteObject]
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

  def apply[A <: ImmutableAccessor](acc: A, id: Zid): Option[CImmutableT[A, TI]] = {
    val obj = acc.getObject(id)
    toT[A, CImmutableT[A, TI]](acc, obj)
  }

  def apply[A <: MutableAccessor](acc: A, id: ZIdentity): Option[CMutableT[A, TM]] = {
    val obj = acc.getObject(id)
    toT[A, CMutableT[A, TM]](acc, obj)
  }

  def getI[A <: ImmutableAccessor](acc: A): TI[A] = {
    ??? //TODO lookup using typeName
  }

  def getA[A <: MutableAccessor](acc: A): TM[A] = {
    ??? //TODO lookup using typeName
  }
}
