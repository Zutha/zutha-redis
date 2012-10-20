package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}


trait ZRef
  extends HasRef
{
  type T <: ZObject
}

  //  def refT[T <: ZObject] = this.asInstanceOf[ReferenceT[T]]
  //  def ref = this.asInstanceOf[ReferenceT[Trait]]


case class IRef protected[model]( acc: ImmutableAccessor, id: Zid )
  extends ZRef
  with HasIRef
{
	type T <: IObject

  def get: IObject = acc.getObject(id).get

  override def ref = this.asInstanceOf[IRefTA[T]]

  def zClass: ZClass = get.zClass

  def fieldSets: IFieldSetMap = get.fieldSets

}

case class MRef protected[model] ( acc: MutableAccessor, id: ZIdentity)
  extends ZRef
with HasMRef
{
	type T <: MObject

  def get: MObject = acc.getObject(id).get

  override def ref = this.asInstanceOf[MRefTA[T]]

  def zClass: MClass = get.zClass

  def fieldSets: MFieldSetMap = get.fieldSets

}
