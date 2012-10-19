package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

trait ZFieldClass
  extends ZClass
  with HasRef[ZFieldClass] {
}

trait ZIFieldClass[A <: ImmutableAccessor]
  extends ZFieldClass
  with ZIClass[A]
  with HasImmutableRef[A, ZIFieldClass[A]]

trait ZMFieldClass[A <: MutableAccessor]
  extends ZFieldClass
  with ZMClass[A]
  with HasMutableRef[A, ZMFieldClass[A]]
