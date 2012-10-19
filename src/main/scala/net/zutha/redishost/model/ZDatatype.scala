package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZDatatype extends ZObjectFactory[ZDatatype, ZIDatatype, ZMDatatype] {
  def typeName = "ZDatatype"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}

trait ZDatatype
  extends ZType
  with HasRef[ZDatatype] {
}

trait ZIDatatype[A <: ImmutableAccessor]
  extends ZDatatype
  with ZIType[A]
  with HasImmutableRef[A, ZIDatatype[A]]

trait ZMDatatype[A <: MutableAccessor]
  extends ZDatatype
  with ZMType[A]
  with HasMutableRef[A, ZMDatatype[A]]
