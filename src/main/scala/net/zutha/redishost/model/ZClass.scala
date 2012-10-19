package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZClass extends ZObjectFactory[ZClass, ZIClass, ZMClass] {
  def typeName = "ZClass"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}
trait ZClass
  extends ZType
  with HasRef[ZClass] {
}

trait ZIClass[A <: ImmutableAccessor]
  extends ZClass
  with ZIType[A]
  with HasImmutableRef[A, ZIClass[A]]

trait ZMClass[A <: MutableAccessor]
  extends ZClass
  with ZMType[A]
  with HasMutableRef[A, ZMClass[A]]
