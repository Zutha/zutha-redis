package net.zutha.redishost.model

import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}

private[model] trait HasRef[+Trait <: HasRef[Trait] with ZObject] {
  def id: ZIdentity
//  def ref: ReferenceT[Trait]


}

private[model] trait HasMutableRef[A <: MutableAccessor, +Trait <: HasMutableRef[A, Trait] with ZMutableObject[A]]
  extends HasRef[Trait] {

  def acc: A

  protected def refT[T <: ZMutableObject[A]]: MReferenceTA[A, T] = {
    ZMutableObjectReference[A, T]( acc, id ).asInstanceOf[MReferenceTA[A, T]]
  }

  def ref: MReferenceTA[A, Trait] = refT[Trait]

  def reload( acc: A, limit: Int ): Trait = ???
}

private[model] trait HasImmutableRef
[A <: ImmutableAccessor, +Trait <: HasImmutableRef[A, Trait] with ZImmutableObject[A]]
  extends HasRef[Trait] {

  override def id: Zid

  def acc: A

  protected def refT[T <: ZImmutableObject[A]]: IReferenceTA[A, T] = {
    ZImmutableObjectReference[A, T]( acc, id ).asInstanceOf[IReferenceTA[A, T]]
  }

  def ref: IReferenceTA[A, Trait] = refT[Trait]

  def reload( acc: A, limit: Int ): Trait = ???
}
