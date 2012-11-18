package net.zutha.redishost.model

protected[redishost] trait ZFieldClassCompanion
[
  T <: ZField,
  TI <: T with IField,
  TM <: T with MField
]
  extends ZClassCompanion[T, TI, TM]
{
  type ObjC = ZFieldClass
  type ObjCM = MFieldClass
  type ObjCI = IFieldClass

  def classFactory = ZFieldClass
}
