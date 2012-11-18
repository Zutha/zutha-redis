package net.zutha.redishost.model

import net.zutha.redishost.db._
import ScopeMatchType._
import MsgType._

object ZFieldSet {


}

/**
 * A container for fields of a certain type owned by a specific item
 */
trait ZFieldSet {
  def parent: ZRef[ZObject]
  def role: ZRef[ZRole]
  def fieldClass: ZRef[ZFieldClass]
  def fields: FieldSeq
  def scopeFilter: ScopeSeq
  def scopeMatchType: ScopeMatchType
  def order: String
  def limit: Int
  def offset: Int

  def reload: ZFieldSet
}


/**
 * A container for immutable fields of a certain type owned by a specific item
 */
case class IFieldSet protected[redishost] ( parent: IRef[IObject],
                                            role: IRef[IRole],
                                            fieldClass: IRef[IFieldClass],
                                            fields: IFieldSeq,
                                            scopeFilter: IScopeSeq,
                                            scopeMatchType: ScopeMatchType,
                                            order: String,
                                            limit: Int,
                                            offset: Int
                                            )( implicit val acc: ImmutableAccessor ) extends ZFieldSet {

  def reload: IFieldSet = {
    acc.getFieldSet( parent, role, fieldClass, scopeFilter, scopeMatchType, order, limit, offset )
    ???
  }
}


/**
 * A FieldSet that can be modified
 */
case class MFieldSet protected[redishost] ( parent: MRef[MObject],
                                            role: MRef[MRole],
                                            fieldClass: MRef[MFieldClass],
                                            fields: MFieldSeq,
                                            scopeFilter: MScopeSeq,
                                            scopeMatchType: ScopeMatchType,
                                            order: String,
                                            limit: Int,
                                            offset: Int,
                                            includeDeleted_? : Boolean,
                                            messages: Seq[(MsgType, String)]
                                            )( implicit val acc: MutableAccessor ) extends ZFieldSet {

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
