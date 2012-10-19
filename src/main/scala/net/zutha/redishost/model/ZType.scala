package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZType extends ZObjectFactory[ZType, ZIType, ZMType] {
  def typeName = "ZType"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}

trait ZType
  extends ZItem
  with HasRef[ZType]{

}

trait ZIType[A <: ImmutableAccessor]
  extends ZType
  with ZIItem[A]
  with HasImmutableRef[A, ZIType[A]]

trait ZMType[A <: MutableAccessor]
  extends ZType
  with ZMItem[A]
  with HasMutableRef[A, ZMType[A]]
