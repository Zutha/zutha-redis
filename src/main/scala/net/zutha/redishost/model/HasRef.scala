package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait HasRef {
  type T <: ZObject

  def id: ZIdentity
  
  def ref: ZRef[T]

}

private[model] trait HasIRef
  extends HasRef
{
  type T <: IObject

  implicit def acc: ImmutableAccessor

  def id: Zids

  def ref: IRef[T] = IRef[T]( id )

  def reload( limit: Int )( implicit acc: ImmutableAccessor ): T = ???
}

private[model] trait HasMRef
  extends HasRef
{
  type T <: MObject

  implicit def acc: MutableAccessor

  def id: ZIdentity

  def ref = MRef[T]( id )

  def reload( limit: Int )( implicit acc: MutableAccessor ): T = ???
}
