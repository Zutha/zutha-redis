package net.zutha.redishost.model.fieldset

import net.zutha.redishost.model._
import itemclass._
import net.zutha.redishost.db.{Accessor, MutableAccessor, ImmutableAccessor}
import ScopeMatchType._

trait ZFieldSetRef[A <: Accessor[A]]
{
  type T <: ZFieldSet[A]

  def parent: ZRef[A, ZObject]
  def role: ZRef[A, ZRole]
  def fieldClass: ZRef[A, ZFieldClass]

  def get( order: String,
           limit: Int,
           offset: Int
           ): T
}

case class IFieldSetRef protected[model]( parent: IRef[ZObject],
                                          role: IRef[ZRole],
                                          fieldClass: IRef[ZFieldClass]
                                          )
                                        ( implicit val acc: ImmutableAccessor )
  extends ZFieldSetRef[IA]
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

case class MFieldSetRef protected[model]( parent: MRef[ZObject],
                                          role: MRef[ZRole],
                                          fieldClass: MRef[ZFieldClass]
                                          )
                                        ( implicit val acc: MutableAccessor )
  extends ZFieldSetRef[MA]
{
  type T = MFieldSet

  def get( order: String,
           limit: Int,
           offset: Int
           ): MFieldSet = {
    get(Seq(), UpperBound, order, limit, offset)
  }

  def get( scopeFilter: MScopeSeq,
           scopeMatchType: ScopeMatchType,
           order: String,
           limit: Int,
           offset: Int,
           includeDeleted_? : Boolean = false
           ): MFieldSet = {
    acc.getFieldSet(parent, role, fieldClass,
      scopeFilter, scopeMatchType, order, limit, offset, includeDeleted_? )
  }
}
