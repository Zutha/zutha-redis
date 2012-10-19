package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}


trait ZRef {

}

  //  def refT[T <: ZObject] = this.asInstanceOf[ReferenceT[T]]
  //  def ref = this.asInstanceOf[ReferenceT[Trait]]


case class IRef
[A <: ImmutableAccessor] protected[model]( acc: A, id: Zid )
  extends ZRef
  with HasImmutableRef[A, IObject[A]]
{
  //  type A0 = acc.type

  def get: IObject[acc.type] = acc.getObject(id).get

  override def refT[T <: IObject[A]] = this.asInstanceOf[IRefTA[A, T]]

  //  override def ref = this.asInstanceOf[IReferenceTA[A, Trait]]

  def zClass: ZClass = get.zClass

  def fieldSets: IFieldSetMap[acc.type] = get.fieldSets

}

case class MRef
[A <: MutableAccessor] protected[model] ( acc: A, id: ZIdentity)
  extends ZRef
  with HasMutableRef[A, MObject[A]]
{
  def get: MObject[acc.type] = acc.getObject(id).get

  override def refT[T <: MObject[A]] = this.asInstanceOf[MRefTA[A, T]]

  def zClass: MClass[acc.type] = get.zClass

  def fieldSets: MFieldSetMap[acc.type] = get.fieldSets

}
