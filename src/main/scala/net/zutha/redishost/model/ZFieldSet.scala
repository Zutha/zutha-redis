package net.zutha.redishost.model

import net.zutha.redishost.db._

object ZFieldSet {


}

/**
 * A container for fields of a certain type owned by a specific item
 */
trait ZFieldSet {
  def parent: RefT[ZObject]
  def role: RefT[ZRole]
  def fieldClass: RefT[ZFieldClass]
//  def fields: FieldMap
  def limit: Int
  def offset: Int

  def reload: ZFieldSet
}


/**
 * A container for immutable fields of a certain type owned by a specific item
 *
 * @param acc the ImmutableAccessor instance that created the fields
 * @param parent
 * @param role
 * @param fieldClass
 * @param fields
 * @param limit
 * @param offset
 * @tparam A value type of the ImmutableAccessor instance that created the fields
 */
case class IFieldSet
[A <: ImmutableAccessor] protected[redishost] ( acc: A,
                                             parent: IRefT[A, IObject],
                                             role: IRefT[A, IRole],
                                             fieldClass: IRefT[A, IFieldClass],
                                             fields: IFieldMap[A],
                                             limit: Int,
                                             offset: Int
                                             ) extends ZFieldSet {

  def reload: IFieldSet[acc.type] = {
//    acc.getFieldSet( parent, role, fieldClass, limit, offset )
    val fields: IFieldMap[acc.type] = ???
//    IFieldSet[acc.type]( acc, parent.ref, role.ref, fieldClass.ref, fields, limit, offset )
    ???
  }
}


/**
 * A FieldSet that can be modified
 *
 * @param acc
 * @param parent
 * @param role
 * @param fieldClass
 * @param fields
 * @param limit
 * @param offset
 * @tparam A
 */
case class MFieldSet
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                           parent: MRefT[A, MObject],
                                           role: MRefT[A, MRole],
                                           fieldClass: MRefT[A, MFieldClass],
                                           fields: MFieldMap[A],
                                           limit: Int,
                                           offset: Int,
                                           includeDeleted_? : Boolean
                                           )
  extends ZFieldSet
{

  // Accessors

  def newField = {
    val field = ZField(acc, fieldClass, role -> parent)
    field
  }

  /** re-load the current page of fields from the database */
  def reload: MFieldSet[acc.type] = {
//    acc.getFieldSet( parent, role, fieldClass, limit, offset, includeDeleted_? )
    ???
  }

}