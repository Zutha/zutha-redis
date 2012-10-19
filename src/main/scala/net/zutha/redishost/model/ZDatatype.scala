package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZDatatype extends ZObjectFactory[ZDatatype, IDatatype, MDatatype] {
  def typeName = "ZDatatype"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZDatatype
  extends ZType
  with HasRef[ZDatatype] {
}

trait IDatatype[A <: ImmutableAccessor]
  extends ZDatatype
  with IType[A]
  with HasImmutableRef[A, IDatatype[A]]

trait MDatatype[A <: MutableAccessor]
  extends ZDatatype
  with MType[A]
  with HasMutableRef[A, MDatatype[A]]
