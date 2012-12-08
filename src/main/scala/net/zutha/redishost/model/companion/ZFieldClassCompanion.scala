package net.zutha.redishost.model.companion

import net.zutha.redishost.model._
import fieldclass._
import itemclass._

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

  type ObjT = ZFieldClass
  type ObjTM = MFieldClass
  type ObjTI = IFieldClass

  protected def classFactory = ZFieldClass
}
