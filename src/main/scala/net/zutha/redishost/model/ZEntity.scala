package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZEntity extends ZObjectFactory[ZEntity, ZIEntity, ZMEntity] {
  def typeName = "ZEntity"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}

trait ZEntity
  extends ZItem
  with HasRef[ZEntity]

trait ZIEntity[A <: ImmutableAccessor]
  extends ZEntity
  with ZIItem[A]
  with HasImmutableRef[A, ZIEntity[A]]

trait ZMEntity[A <: MutableAccessor]
  extends ZEntity
  with ZMItem[A]
  with HasMutableRef[A, ZMEntity[A]]
