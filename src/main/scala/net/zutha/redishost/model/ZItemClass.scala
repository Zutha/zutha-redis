package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZItemClass extends ZObjectFactory[ZItemClass, ZIItemClass, ZMItemClass] {
  def typeName = "ZItemClass"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}

trait ZItemClass
  extends ZClass
  with HasRef[ZItemClass] {
}

trait ZIItemClass[A <: ImmutableAccessor]
  extends ZItemClass
  with ZIClass[A]
  with HasImmutableRef[A, ZIItemClass[A]]

trait ZMItemClass[A <: MutableAccessor]
  extends ZItemClass
  with ZMClass[A]
  with HasMutableRef[A, ZMItemClass[A]]
