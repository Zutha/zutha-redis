package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait HasRef {
  type T <: ZObject

  def id: ZIdentity
  
  def ref: RefT[T]

}

private[model] trait HasIRef
  extends HasRef
{
  type T <: IObject

  def acc: ImmutableAccessor

  def id: Zids

  def ref: IRefTA[T] = IRef( acc, id ).asInstanceOf[IRefTA[T]]

  def reload( acc: ImmutableAccessor, limit: Int ): T = ???
}

private[model] trait HasMRef
  extends HasRef
{
  type T <: MObject

  def acc: MutableAccessor

  def id: ZIdentity

  def ref = MRef( acc, id ).asInstanceOf[MRefTA[T]]

  def reload( acc: MutableAccessor, limit: Int ): T = ???
}
