package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import fieldclass._
import itemclass._

protected[redishost] trait ZFieldClassSingleton
[
  T <: ZField,
  TI <: T with IField,
  TM <: T with MField
]
  extends ZClassSingleton[T, TI, TM]
  with ZItemSingleton[ZFieldClass, IFieldClass, MFieldClass]
{

}
