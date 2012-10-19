package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

trait ZFieldClass
  extends ZClass
  with HasRef[ZFieldClass] {
}

trait IFieldClass[A <: ImmutableAccessor]
  extends ZFieldClass
  with IClass[A]
  with HasImmutableRef[A, IFieldClass[A]]

trait MFieldClass[A <: MutableAccessor]
  extends ZFieldClass
  with MClass[A]
  with HasMutableRef[A, MFieldClass[A]]
