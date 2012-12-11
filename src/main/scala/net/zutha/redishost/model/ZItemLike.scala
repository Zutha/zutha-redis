package net.zutha.redishost.model

import itemclass._
import scala.reflect.runtime.universe._

private[model] trait ZItemLike[+This <: ZItem]
  extends ZObjectLike[This]
{
  self: This =>

  override def zClass: ZRef[ZItemClass]
}

private[model] trait IItemLike[+This <: ZItem]
  extends ZItemLike[This]
  with IObjectLike[This]
{
  self: This =>

  override def zClass: IIRef[ZItemClass]
}

private[model] trait MItemLike[+This <: ZItem]
  extends ZItemLike[This]
  with MObjectLike[This]
{
  self: This =>

  override def zClass: MIRef[ZItemClass]

  protected def changeClass[T >: L <: MItem: TypeTag]( newClass: MRef[ZItemClass] ): T = {
    acc.changeItemClass( this.ref, newClass )
    reload[T]
  }

}