package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

object ZRole extends ZObjectFactory[ZRole, ZIRole, ZMRole] {
  def typeName = "ZRole"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}

trait ZRole
  extends ZType
  with HasRef[ZRole] {


}

trait ZIRole[A <: ImmutableAccessor]
  extends ZRole
  with ZIType[A]
  with HasImmutableRef[A, ZIRole[A]]
{

}

trait ZMRole[A <: MutableAccessor]
  extends ZRole
  with ZMType[A]
  with HasMutableRef[A, ZMRole[A]]
