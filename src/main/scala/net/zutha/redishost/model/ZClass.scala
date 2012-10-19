package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZClass extends ZObjectFactory[ZClass, IClass, MClass] {
  def typeName = "ZClass"

  def validType_?(obj: ZObject): Boolean = ???
}
trait ZClass
  extends ZType
  with HasRef[ZClass] {
}

trait IClass[A <: ImmutableAccessor]
  extends ZClass
  with IType[A]
  with HasImmutableRef[A, IClass[A]]

trait MClass[A <: MutableAccessor]
  extends ZClass
  with MType[A]
  with HasMutableRef[A, MClass[A]]
