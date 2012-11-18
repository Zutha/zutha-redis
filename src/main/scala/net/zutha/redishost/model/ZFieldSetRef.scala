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
    get(Seq(), UpperBound, order, limit, offset)
  }
  def get( scopeFilter: IScopeSeq,
           scopeMatchType: ScopeMatchType,
           order: String,
           limit: Int,
           offset: Int
           ): T = {
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
           offset: Int
           ): T = {
    get(Seq(), UpperBound, order, limit, offset)
  }
  def get( scopeFilter: MScopeSeq,
           scopeMatchType: ScopeMatchType,
           order: String,
           limit: Int,
           offset: Int,
           includeDeleted_? : Boolean = false
           ): T = {
    acc.getFieldSet(parent, role, fieldClass,
      scopeFilter, scopeMatchType, order, limit, offset, includeDeleted_? )
  }
}
