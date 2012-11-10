package net.zutha.redishost.model

import net.zutha.redishost.db._

object ZFieldSet {


}

/**
 * A container for fields of a certain type owned by a specific item
 */
trait ZFieldSet {
  def parent: ZRef[ZObject]
  def role: ZRef[ZRole]
  def fieldClass: ZRef[ZFieldClass]
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
 */
case class IFieldSet protected[redishost] ( acc: ImmutableAccessor,
                                             parent: IRef[IObject],
                                             role: IRef[IRole],
                                             fieldClass: IRef[IFieldClass],
                                             fields: IFieldMap,
                                             limit: Int,
                                             offset: Int
                                             ) extends ZFieldSet {

  def reload: IFieldSet = {
//    acc.getFieldSet( parent, role, fieldClass, limit, offset )
    val fields: IFieldMap = ???
//    IFieldSet( acc, parent.ref, role.ref, fieldClass.ref, fields, limit, offset )
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
 */
case class MFieldSet protected[redishost] ( acc: MutableAccessor,
                                            parent: MRef[MObject],
                                            role: MRef[MRole],
                                            fieldClass: MRef[MFieldClass],
                                            fields: MFieldMap,
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
  def reload: MFieldSet = {
//    acc.getFieldSet( parent, role, fieldClass, limit, offset, includeDeleted_? )
    ???
  }

}