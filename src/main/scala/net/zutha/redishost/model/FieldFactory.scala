package net.zutha.redishost.model

protected[redishost] trait FieldFactory
[
T <: ZField,
TI <: T with IField,
TM <: T with MField
]
  extends ObjectFactory[T, TI, TM]
{
  type ObjT = ZFieldClass
  type ObjTM = MFieldClass
  type ObjTI = IFieldClass

  def classFactory = ZFieldClass
}
