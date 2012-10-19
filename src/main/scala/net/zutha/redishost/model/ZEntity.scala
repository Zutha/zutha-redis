package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZEntity extends ZObjectFactory[ZEntity, IEntity, MEntity] {
  def typeName = "ZEntity"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZEntity
  extends ZItem
  with HasRef[ZEntity]

trait IEntity[A <: ImmutableAccessor]
  extends IItem[A]
  with ZEntity
  with HasImmutableRef[A, IEntity[A]]

trait MEntity[A <: MutableAccessor]
  extends ZEntity
  with MItem[A]
  with HasMutableRef[A, MEntity[A]]
