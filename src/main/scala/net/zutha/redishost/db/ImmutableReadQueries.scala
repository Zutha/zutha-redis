package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldset.IFieldSet
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import net.zutha.redishost.model.IRef

trait ImmutableReadQueries
  extends ReadQueries[ImmutableAccessor]
{
  self: ImmutableAccessor =>

  // =================== Object Getters ======================


  // =================== Generic Queries ======================


  // =================== Object Member Getters ======================


  //  TODO: implement stub
  def getFieldSet( parent: IRef[ZObject],
                   role: IRef[ZRole],
                   fieldClass: IRef[ZFieldClass],
                   scopeFilter: IScopeSeq,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int
                   ): IFieldSet = {
    val fields: IFieldSeq = ???
    IFieldSet( parent, role, fieldClass, fields, scopeFilter, scopeMatchType, order, limit, offset )
  }

}
