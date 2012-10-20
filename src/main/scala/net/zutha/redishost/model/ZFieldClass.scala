package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

trait ZFieldClass
  extends ZClass
{
	type T <: ZFieldClass
}

trait IFieldClass
  extends ZFieldClass
  with IClass
{
	type T <: IFieldClass
}

trait MFieldClass
  extends ZFieldClass
  with MClass
{
	type T <: MFieldClass
}
