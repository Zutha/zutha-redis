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

trait IDatatype
  extends ZDatatype
  with IType
{
	type T <: IDatatype
}

trait MDatatype
  extends ZDatatype
  with MType
{
	type T <: MDatatype
}
