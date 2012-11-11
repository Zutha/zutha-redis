package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.model.ScopeMatchType._


trait ZFieldSetRef
{
  type T <: ZFieldSet

  def parent: ZRef[ZObject]
  def role: ZRef[ZRole]
  def fieldClass: ZRef[ZFieldClass]

  def get( order: String,
           limit: Int,
           offset: Int
           ): T
}

case class IFieldSetRef protected[model]( acc: ImmutableAccessor,
                                          parent: IRef[IObject],
                                          role: IRef[IRole],
                                          fieldClass: IRef[IFieldClass]
                                          )
  extends ZFieldSetRef
{
  type T = IFieldSet

  def get( order: String,
           limit: Int,
           offset: Int
          ): T = {
    val scopeFilter: IScope = List()
    val scopeMatchType: ScopeMatchType = UpperBound
    acc.getFieldSet(parent, role, fieldClass,
      scopeFilter, scopeMatchType, order, limit, offset)
  }
}

case class MFieldSetRef protected[model]( acc: MutableAccessor,
                                          parent: MRef[MObject],
                                          role: MRef[MRole],
                                          fieldClass: MRef[MFieldClass]
                                          )
  extends ZFieldSetRef
{
  type T = MFieldSet

  def get( order: String,
           limit: Int,
           offset: Int) = {
    val scopeFilter: MScope = List()
    val scopeMatchType: ScopeMatchType = UpperBound
    acc.getFieldSet(parent, role, fieldClass,
      scopeFilter, scopeMatchType, order, limit, offset, includeDeleted_? =  false)
  }
}
