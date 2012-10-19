package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZDatatype extends ZObjectFactory[ZDatatype, IDatatype, MDatatype] {
  def typeName = "ZDatatype"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZDatatype
  extends ZType
{
	type T <: ZDatatype
}

trait IDatatype[A <: ImmutableAccessor]
  extends ZDatatype
  with IType[A]
{
	type T <: IDatatype[A]
}

trait MDatatype[A <: MutableAccessor]
  extends ZDatatype
  with MType[A]
{
	type T <: MDatatype[A]
}
