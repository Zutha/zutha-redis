package net.zutha.redishost.model

object ZDatatype extends ZItemClassCompanion[ZDatatype, IDatatype, MDatatype] {

  def name = "ZDatatype"

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
