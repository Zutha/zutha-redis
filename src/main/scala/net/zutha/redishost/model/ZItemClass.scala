package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZItemClass extends ZObjectFactory[ZItemClass, IItemClass, MItemClass] {
  def typeName = "ZItemClass"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZItemClass
  extends ZClass
  with HasRef[ZItemClass] {
}

trait IItemClass[A <: ImmutableAccessor]
  extends ZItemClass
  with IClass[A]
  with HasImmutableRef[A, IItemClass[A]]

trait MItemClass[A <: MutableAccessor]
  extends ZItemClass
  with MClass[A]
  with HasMutableRef[A, MItemClass[A]]
