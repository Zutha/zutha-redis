package net.zutha.redishost.model.fieldset

import net.zutha.redishost.model._
import fieldclass._
import itemclass._

import net.zutha.redishost.db._
import ScopeMatchType._
import MsgType._

object ZFieldSet {


}

/**
 * A container for fields of a certain type owned by a specific item
 */
trait ZFieldSet[A <: Accessor[A]] {
  def parent: ZRef[A, ZObject]
  def role: ZRef[A, ZRole]
  def fieldClass: ZRef[A, ZFieldClass]
  def fields: FieldSeq[A]
  def scopeFilter: ScopeSeq[A]
  def scopeMatchType: ScopeMatchType
  def order: String
  def limit: Int
  def offset: Int

  def reload: ZFieldSet[A]
}


/**
 * A container for immutable fields of a certain type owned by a specific item
 */
case class IFieldSet protected[redishost] ( parent: IRef[ZObject],
                                            role: IRef[ZRole],
                                            fieldClass: IRef[ZFieldClass],
                                            fields: IFieldSeq,
                                            scopeFilter: IScopeSeq,
                                            scopeMatchType: ScopeMatchType,
                                            order: String,
                                            limit: Int,
                                            offset: Int
                                            )
                                          ( implicit val acc: ImmutableAccessor )
  extends ZFieldSet[IA]
{

  def reload: IFieldSet = {
    acc.getFieldSet( parent, role, fieldClass, scopeFilter, scopeMatchType, order, limit, offset )
    ???
  }
}


/**
 * A FieldSet that can be modified
 */
case class MFieldSet protected[redishost] ( parent: MRef[ZObject],
                                            role: MRef[ZRole],
                                            fieldClass: MRef[ZFieldClass],
                                            fields: MFieldSeq,
                                            scopeFilter: MScopeSeq,
                                            scopeMatchType: ScopeMatchType,
                                            order: String,
                                            limit: Int,
                                            offset: Int,
                                            messages: Seq[(MsgType, String)],
                                            includeDeleted_? : Boolean
                                            )
                                          ( implicit val acc: MutableAccessor )
  extends ZFieldSet[MA]
{

  // Accessors

  def newField = {
    val field = ZField(fieldClass, role -> parent)
    field
  }

  /** re-load the current page of fields from the database */
  def reload: MFieldSet = {
    acc.getFieldSet( parent, role, fieldClass, scopeFilter, scopeMatchType, order, limit, offset, includeDeleted_? )
    ???
  }

}
