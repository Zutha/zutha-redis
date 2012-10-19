package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZLiteralType extends ZObjectFactory[ZLiteralType, ZILiteralType, ZMLiteralType] {
  def typeName = "ZLiteralType"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}

trait ZLiteralType
  extends ZType
  with HasRef[ZLiteralType] {
}

trait ZILiteralType[A <: ImmutableAccessor]
  extends ZLiteralType
  with ZIType[A]
  with HasImmutableRef[A, ZILiteralType[A]]

trait ZMLiteralType[A <: MutableAccessor]
  extends ZLiteralType
  with ZMType[A]
  with HasMutableRef[A, ZMLiteralType[A]]
