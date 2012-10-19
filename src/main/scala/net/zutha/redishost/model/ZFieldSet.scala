package net.zutha.redishost.model

import net.zutha.redishost.db._

object ZFieldSet {


}

/**
 * A container for fields of a certain type owned by a specific item
 */
trait ZFieldSet {
  def parent: ReferenceT[ZObject]
  def role: ReferenceT[ZRole]
  def fieldClass: ReferenceT[ZFieldClass]
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
case class ZImmutableFieldSet
[A <: ImmutableAccessor] protected[redishost] ( acc: A,
                                             parent: IReferenceT[A, ZImmutableObject],
                                             role: IReferenceT[A, ZIRole],
                                             fieldClass: IReferenceT[A, ZIFieldClass],
                                             fields: IFieldMap[A],
                                             limit: Int,
                                             offset: Int
                                             ) extends ZFieldSet {

  def reload: ZImmutableFieldSet[acc.type] = {
//    acc.getFieldSet( parent, role, fieldClass, limit, offset )
    val fields: IFieldMap[acc.type] = ???
//    ZImmutableFieldSet[acc.type]( acc, parent.ref, role.ref, fieldClass.ref, fields, limit, offset )
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
case class ZMutableFieldSet
[A <: MutableAccessor] protected[redishost] ( acc: A,
                                           parent: MReferenceT[A, ZMutableObject],
                                           role: MReferenceT[A, ZMRole],
                                           fieldClass: MReferenceT[A, ZMFieldClass],
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
  def reload: ZMutableFieldSet[acc.type] = {
//    acc.getFieldSet( parent, role, fieldClass, limit, offset, includeDeleted_? )
    ???
  }

}