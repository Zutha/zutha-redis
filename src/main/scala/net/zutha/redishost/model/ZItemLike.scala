package net.zutha.redishost.model

import itemclass._
import scala.reflect.runtime.universe._

trait ZItemLike[+This <: ZItem]
  extends ZObjectLike[This]
{
  self: This =>

  override def zClass: ZRef[ZItemClass]
}

trait IItemLike[+This <: IItem]
  extends ZItemLike[This]
  with IObjectLike[This]
{
  self: This =>

  override def zClass: IRef[IItemClass]
}

trait MItemLike[+This <: MItem]
  extends ZItemLike[This]
  with MObjectLike[This]
{
  self: This =>

  override def zClass: MRef[MItemClass]

  protected def changeClass[T >: L <: MItem: TypeTag]( newClass: MRef[MItemClass] ): T = {
    acc.changeItemClass( this.ref, newClass )
    reload[T]
  }

}