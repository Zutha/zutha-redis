package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait HasRef {
  type T <: ZObject

  def id: ZIdentity
  
  def ref: RefT[T]

}

private[model] trait HasIRef
[A <: ImmutableAccessor]
  extends HasRef
{
  type T <: IObject[A]

  def id: Zid
  def acc: A

  def ref: IRefTA[A, T] = IRef( acc, id ).asInstanceOf[IRefTA[A, T]]

  def reload( acc: A, limit: Int ): T = ???
}

private[model] trait HasMRef[A <: MutableAccessor]
  extends HasRef
{
  type T <: MObject[A]

  def acc: A

  def ref = MRef[A]( acc, id ).asInstanceOf[MRefTA[A, T]]

  def reload( acc: A, limit: Int ): T = ???
}
