package net.zutha.redishost.model

protected[redishost] trait ItemFactory
[
  T <: ZItem,
  TI <: T with IItem,
  TM <: T with MItem
]
  extends ObjectFactory[T, TI, TM]
{
  type ObjT = ZItemClass
  type ObjTM = MItemClass
  type ObjTI = IItemClass

  def classFactory = ZItemClass
}
