package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}

trait ZFieldSetRef
{
  type T <: ZFieldSet

  def parent: ZRef[ZObject]
  def role: ZRef[ZRole]
  def fieldClass: ZRef[ZFieldClass]

  def get(limit: Int, offset: Int): T
}

case class IFieldSetRef protected[model]( acc: ImmutableAccessor,
                                          parent: IRef[IObject],
                                          role: IRef[IRole],
                                          fieldClass: IRef[IFieldClass]
                                          )
  extends ZFieldSetRef
{
  type T = IFieldSet

  def get(limit: Int, offset: Int) = acc.getFieldSet(parent, role, fieldClass, limit, offset)
}

case class MFieldSetRef protected[model]( acc: MutableAccessor,
                                          parent: MRef[MObject],
                                          role: MRef[MRole],
                                          fieldClass: MRef[MFieldClass]
                                          )
  extends ZFieldSetRef
{
  type T = MFieldSet

  def get(limit: Int, offset: Int) = acc.getFieldSet(parent, role, fieldClass, limit, offset, includeDeleted_? =  false)
}
