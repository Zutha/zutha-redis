package net.zutha.redishost.model

import itemclass.{MObject, ZObject}
import special.ZNothing
import scala.reflect.runtime.universe._


trait Loadable[+This <: ZObject, +ZT >: ZNothing <: ZObject]
  extends Referenceable[ZT]
{
  self: This with ZT =>

  type Impl = This

  def load: Obj[This, ZT] = this

  def reload[
    ImpU >: Impl <: MObject: TypeTag,
    ZU >: ZT <: ZObject: TypeTag
  ]: Obj[ImpU, ZU] = {
    acc.loadObject[ImpU, ZU]( zRef[ZU] )
  }
}
