package net.zutha.redishost.model

import net.zutha.redishost.db.DB
import common._

/**
 *
 */
object ZFieldSet {
  /** load FieldSet */
  def apply( parent: ZObject,
             role: ZRole,
             fieldClass: ZFieldClass,
             limit: Int = 0,
             offset: Int = 0
            ): ZFieldSet = {
    val persistedFields = DB.getFieldSetFields( parent, role, fieldClass, limit, offset )
    new ZPersistedFieldSet( parent, role, fieldClass,
      persistedFields, limit, offset )
  }

  /** load FieldSet and add modifications */
  protected def apply( parent: ZObject, role: ZRole, fieldClass: ZFieldClass,
                       addedFields: Map[ZFieldIdentity, ZNewField],
                       modifiedFields: Map[ZFieldIdentity, ZModifiedField],
                       limit: Int,
                       offset: Int
                      ): ZFieldSet = {
    val currentPageFields = DB.getFieldSetFields( parent, role, fieldClass, limit, offset )
    val modifiedFieldsUpdated = DB.getUpdatedFields( modifiedFields.values.toSeq:_* )
    val persistedFields = currentPageFields ++ modifiedFieldsUpdated
    new ZModifiedFieldSet( parent, role, fieldClass,
      persistedFields, addedFields, modifiedFields, limit, offset )
  }
}

/**
 * A container for fields of a certain type owned by a specific item
 */
trait ZFieldSet {
  def parent: ZObject
  def role: ZRole
  def fieldClass: ZFieldClass
  def fields: Map[ZFieldIdentity, ZField]
}



case class
ZPersistedFieldSet protected[model] ( parent: ZObject,
                                      role: ZRole,
                                      fieldClass: ZFieldClass,
                                      fields: Map[ZFieldIdentity, ZPersistedField],
                                      limit: Int,
                                      offset: Int
                                      ) extends ZFieldSet {

  def edit: ZModifiedFieldSet =
    ZModifiedFieldSet( parent, role, fieldClass, fields, Map(), Map(), limit, offset )

  def reload: ZPersistedFieldSet = {
    val persistedFields = DB.getFieldSetFields( parent, role, fieldClass, limit, offset )
    ZPersistedFieldSet( parent, role, fieldClass, persistedFields, limit, offset )
  }
}


/**
 * A FieldSet that can be modified
 */
trait ZMutableFieldSet extends ZFieldSet {

}



/**
 * A persisted FieldSet that possibly has unsaved modifications
 *
 * @param parent
 * @param role
 * @param fieldClass
 * @param persistedFields
 * @param addedFields
 * @param modifiedFields
 * @param limit
 * @param offset
 */
case class
ZModifiedFieldSet protected[model] ( override val parent: ZObject,
                                     override val role: ZRole,
                                     override val fieldClass: ZFieldClass,
                                     persistedFields: Map[ZFieldIdentity, ZPersistedField],
                                     addedFields: Map[ZFieldIdentity, ZNewField],
                                     modifiedFields: Map[ZFieldIdentity, ZModifiedField],
                                     limit: Int,
                                     offset: Int
                                     )
  extends ZFieldSet with ZMutableFieldSet {

  // Accessors

  protected def persistedFieldsUpdated: Map[ZFieldIdentity, ZModifiedField] =
    (persistedFields.mapValues(_.edit)) ++ modifiedFields

  override def fields = fields()
  def fields(showDeleted: Boolean = false): Map[ZFieldIdentity, ZMutableField] = {
    val allFields: Map[ZFieldIdentity, ZMutableField] = addedFields ++ persistedFieldsUpdated
    if (showDeleted) allFields else allFields.filterNot(_._2.deleted_? )
  }

  // Mutators
  protected def update( addedFields: Map[ZFieldIdentity, ZNewField] = addedFields,
                        modifiedFields: Map[ZFieldIdentity, ZModifiedField] = modifiedFields,
                        limit: Int = limit,
                        offset: Int = offset
            ): ZModifiedFieldSet = {
    ZModifiedFieldSet( parent, role, fieldClass, persistedFields, addedFields, modifiedFields, limit, offset )
  }

  def newField = {
    val field = ZField(fieldClass, role -> parent)
    val newAddedFields = addedFields + ((field.id, field))
    update( addedFields = newAddedFields )
  }

  def updateFields(fields: ZMutableField*): ZModifiedFieldSet = {
    val updatedNewFields = fields flatMap {f => f match {
      case nf: ZNewField => Some(nf)
      case _ => None
    }}
    val updatedModifiedFields = fields flatMap {f => f match {
      case mf: ZModifiedField => Some(mf)
      case _ => None
    }}
    val newAddedFields = (addedFields /: updatedNewFields) { (mfs, f) =>
      require(addedFields.contains(f.id))
      mfs + ((f.id, f))
    }
    val newModifiedFields = (modifiedFields /: updatedModifiedFields) {(mfs, f) =>
      require(persistedFields.contains(f.id))
      mfs + ((f.id, f))
    }
    update( addedFields = newAddedFields, modifiedFields = newModifiedFields)
  }

  def removeField(field: ZModifiedField): ZFieldSet = updateFields(field.delete)

  /** load the latest changes from the database and merge in the modifications */
  def reload( limit: Int, offset: Int = 0 ): ZModifiedFieldSet = {
    val currentPageFields = DB.getFieldSetFields( parent, role, fieldClass, limit, offset )
    val modifiedFieldsUpdated = DB.getUpdatedFields( modifiedFields.values.toSeq:_* )
    val persistedFields = currentPageFields ++ modifiedFieldsUpdated
    //TODO merge modifications
    new ZModifiedFieldSet( parent, role, fieldClass,
      persistedFields, addedFields, modifiedFields, limit, offset )
  }

  // Persistence

  def save() {}

}


/**
 * A FieldSet that has not yet persisted to the database
 *
 * @param parent
 * @param role
 * @param fieldClass
 * @param fields
 */
case class ZNewFieldSet protected[model] ( parent: ZObject,
                                           role: ZRole,
                                           fieldClass: ZFieldClass,
                                           fields: Map[ZFieldIdentity, ZNewField]
                                           ) extends ZFieldSet with ZMutableFieldSet {

}