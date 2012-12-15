package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass.ZField
import fieldset.IFieldSet
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import net.zutha.redishost.model.IRef
import scala.reflect.runtime.universe._

trait ImmutableReadQueries
  extends ReadQueries[ImmutableAccessor]
{
  self: ImmutableAccessor =>

  // =================== Object Getters ======================

  protected def retrieveItem[Impl <: ZItem: TypeTag]( key: String ): Impl = {
    ???
  }

  protected def retrieveField[Impl <: ZField: TypeTag]( key: String ): Impl = {
    ???
  }


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
