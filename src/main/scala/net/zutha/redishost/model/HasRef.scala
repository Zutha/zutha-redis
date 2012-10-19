package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait HasRef[Trait <: ZObject] {
  def id: ZIdentity
//  def ref: ReferenceT[Trait]


}

private[model] trait HasMutableRef[A <: MutableAccessor, Trait <: MObject[A]]
  extends HasRef[Trait] {

  def acc: A

  protected def refT[T <: MObject[A]]: MRefTA[A, T] = {
    MRef[A]( acc, id ).asInstanceOf[MRefTA[A, T]]
  }

  def ref: MRefTA[A, Trait] = refT[Trait]

  def reload( acc: A, limit: Int ): Trait = ???
}

private[model] trait HasImmutableRef
[A <: ImmutableAccessor, +Trait <: HasImmutableRef[A, Trait] with IObject[A]]
  extends HasRef[Trait] {

  override def id: Zid

  def acc: A

  protected def refT[T <: IObject[A]]: IRefTA[A, T] = {
    IRef[A]( acc, id ).asInstanceOf[IRefTA[A, T]]
  }

  def ref: IRefTA[A, Trait] = refT[Trait]

  def reload( acc: A, limit: Int ): Trait = ???
}
