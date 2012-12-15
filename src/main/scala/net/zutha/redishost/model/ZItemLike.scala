package net.zutha.redishost.model

import itemclass._
import scala.reflect.runtime.universe._

private[model]
trait ZItemLike
  extends ZObjectLike
  with Loadable[ZItem, ZItem]
{
  self: ZItem =>

  override def zClass: ZRef[A, ZItemClass]
}

private[model]
trait IItemLike
  extends ZItemLike
  with IObjectLike
  with Loadable[IItem, ZItem]
{
  self: IItem =>

  override def zClass: IRef[ZItemClass]
}

private[model]
trait MItemLike
  extends ZItemLike
  with MObjectLike
  with Loadable[MItem, ZItem]
{
  self: MItem =>

  override def zClass: MRef[ZItemClass]

  protected def changeClass[T >: Impl <: MItem: TypeTag]( newClass: MRef[ZItemClass] ): T = {
    acc.changeItemClass( this.zRef, newClass )
    reload
  }

}