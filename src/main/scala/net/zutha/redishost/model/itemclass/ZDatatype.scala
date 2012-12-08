package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion

object ZDatatype extends ZItemClassCompanion[ZDatatype, IDatatype, MDatatype] {

  def name = "Datatype"

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
