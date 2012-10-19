package net.zutha.redishost.model

import net.zutha.redishost.db.{Accessor, ImmutableAccessor, MutableAccessor}

object ZType extends ZObjectFactory[ZType, IType, MType] {
  def typeName = "ZType"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZType
  extends ZItem
  with HasRef[ZType]{

}

trait IType[A <: ImmutableAccessor]
  extends ZType
  with IItem[A]
  with HasImmutableRef[A, IType[A]]

trait MType[A <: MutableAccessor]
  extends ZType
  with MItem[A]
  with HasMutableRef[A, MType[A]]
