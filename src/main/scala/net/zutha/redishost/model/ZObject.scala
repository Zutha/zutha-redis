package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}

object ZObject extends ZObjectFactory[ZObject, IObject, MObject] {
  def typeName = "ZObject"

  def validType_?(obj: ZObject): Boolean = ???
}


trait ZObject
  extends HasRef
{
  type T <: ZObject

  // Accessors
  def ref: RefT[T]

  def id: ZIdentity
  def zClass: ZClass
//  def fieldSets: FieldSetMap

  def zids: Set[Zid] = id match {
    case TempId(_) => Set()
    case Zids(_, allZids) => allZids
    case zid: Zid => Set(zid)
  }
  def primaryZids: Set[Zid] = id match {
    case TempId(_) => Set()
    case Zids(pZids, _) => pZids
    case zid: Zid => Set(zid)
  }
  def zid: Option[Zid] = primaryZids.toSeq.sorted.headOption

  def persisted_? = primaryZids.size > 0

  def merged_? = primaryZids.size >= 2

  // Comparison

  def sameAs(other: ZObject): Boolean = id == other.id


}

trait PersistedObject
  extends ZObject
{
	type T <: PersistedObject
}


/**
 * An immutable Object that corresponds to an Object in the database
 */
trait IObject
  extends ZObject
  with PersistedObject
  with HasIRef
{
	type T <: IObject

  def acc: ImmutableAccessor

  def id: Zid

  override def zClass: IClass

  def fieldSets: IFieldSetMap

}


/**
 * An Object that can be Modified
 */
trait MObject
  extends ZObject
  with HasMRef
{
	type T <: MObject

  def acc: MutableAccessor

  // Accessors
  override def zClass: MClass

  def fieldSets: MFieldSetMap

  def deleted_? : Boolean

  // Mutation

  protected def update( fieldSets: MFieldSetMap = fieldSets,
                        deleted_? : Boolean = deleted_?
                        ): T

  def mutateFieldSets(mutate: MFieldSet => MFieldSet): T = {
    val newFieldSets = fieldSets.mapValues(mutate)
    update(fieldSets = newFieldSets)
  }
  def mutateFieldSet( role: MRole, fieldClass: MFieldClass )
                    ( mutate: MFieldSet => MFieldSet ): T = {

    val key = (role.ref -> fieldClass.ref)

    fieldSets.get(key) match {
      case Some(fieldSet) => update(fieldSets = fieldSets.updated(role.ref -> fieldClass.ref, mutate(fieldSet)))
      case None => throw new IllegalArgumentException(
        "object does not contain the specified fieldSet"
      )
    }
  }

  def delete: T = update( deleted_? = true )
  def restore: T = update( deleted_? = false )

}


/**
 * A Persisted Object that possibly has unsaved modifications
 */
trait ModifiedObject
  extends MObject
  with PersistedObject
{
	type T <: ModifiedObject

  def id: ZPersistedIdentity

}


/**
 * An Object that has not been persisted to the database
 */
trait NewObject
  extends MObject
{
	type T <: NewObject

  def id: TempId

}



