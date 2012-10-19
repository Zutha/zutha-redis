package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

trait ZFieldClass
  extends ZClass
{
	type T <: ZFieldClass
}

trait IFieldClass[A <: ImmutableAccessor]
  extends ZFieldClass
  with IClass[A]
{
	type T <: IFieldClass[A]
}

trait MFieldClass[A <: MutableAccessor]
  extends ZFieldClass
  with MClass[A]
{
	type T <: MFieldClass[A]
}
