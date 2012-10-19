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
trait IObject[A <: ImmutableAccessor]
  extends ZObject
  with PersistedObject
  with HasIRef[A]
{
	type T <: IObject[A]

  def acc: A

  def id: Zid

  override def zClass: IClass[A]

  def fieldSets: IFieldSetMap[A]

}


/**
 * An Object that can be Modified
 */
trait MObject[A <: MutableAccessor]
  extends ZObject
  with HasMRef[A]
{
	type T <: MObject[A]

  def acc: A

  // Accessors
  override def zClass: MClass[A]

  def fieldSets: MFieldSetMap[A]

  def deleted_? : Boolean

  // Mutation

  protected def update( fieldSets: MFieldSetMap[A] = fieldSets,
                        deleted_? : Boolean = deleted_?
                        ): T

  def mutateFieldSets(mutate: MFieldSet[A] => MFieldSet[A]): T = {
    val newFieldSets = fieldSets.mapValues(mutate)
    update(fieldSets = newFieldSets)
  }
  def mutateFieldSet( role: MRole[A], fieldClass: MFieldClass[A] )
                    ( mutate: MFieldSet[A] => MFieldSet[A] ): T = {

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
trait ModifiedObject[A <: MutableAccessor]
  extends MObject[A]
  with PersistedObject
{
	type T <: ModifiedObject[A]

  def id: ZPersistedIdentity

}


/**
 * An Object that has not been persisted to the database
 */
trait NewObject[A <: MutableAccessor]
  extends MObject[A]
{
	type T <: NewObject[A]

  def id: TempId

}



