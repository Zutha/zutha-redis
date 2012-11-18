package net.zutha.redishost.model

protected[redishost] trait ZItemClassCompanion
[
  T <: ZItem,
  TI <: T with IItem,
  TM <: T with MItem
]
  extends ZClassCompanion[T, TI, TM]
{
  type ObjC = ZItemClass
  type ObjCM = MItemClass
  type ObjCI = IItemClass

  def classFactory = ZItemClass
}
