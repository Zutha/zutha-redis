package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}


trait ZRef
  extends HasRef
{
  type T <: ZObject
}

  //  def refT[T <: ZObject] = this.asInstanceOf[ReferenceT[T]]
  //  def ref = this.asInstanceOf[ReferenceT[Trait]]


case class IRef
[A <: ImmutableAccessor] protected[model]( acc: A, id: Zid )
  extends ZRef
  with HasIRef[A]
{
	type T <: IObject[A]

  def get: IObject[acc.type] = acc.getObject(id).get

  override def ref = this.asInstanceOf[IRefTA[A, T]]

  def zClass: ZClass = get.zClass

  def fieldSets: IFieldSetMap[acc.type] = get.fieldSets

}

case class MRef
[A <: MutableAccessor] protected[model] ( acc: A, id: ZIdentity)
  extends ZRef
with HasMRef[A]
{
	type T <: MObject[A]

  def get: MObject[acc.type] = acc.getObject(id).get

  override def ref = this.asInstanceOf[MRefTA[A, T]]

  def zClass: MClass[acc.type] = get.zClass

  def fieldSets: MFieldSetMap[acc.type] = get.fieldSets

}
