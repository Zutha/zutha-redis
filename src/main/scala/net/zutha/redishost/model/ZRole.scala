package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZRole extends ZObjectFactory[ZRole, IRole, MRole] {
  def typeName = "ZRole"

  def validType_?(obj: ZObject): Boolean = ???
}

trait ZRole
  extends ZType
  with HasRef[ZRole] {


}

trait IRole[A <: ImmutableAccessor]
  extends ZRole
  with IType[A]
  with HasImmutableRef[A, IRole[A]]
{

}

trait MRole[A <: MutableAccessor]
  extends ZRole
  with MType[A]
  with HasMutableRef[A, MRole[A]]
