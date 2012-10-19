package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}

object ZObject extends ZObjectFactory[ZObject, ZImmutableObject, ZMutableObject] {
  def typeName = "ZObject"

  def validType_?(obj: ZConcreteObject): Boolean = ???
}


trait ZObject
  extends HasRef[ZObject]
{

  // Accessors

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



trait ZConcreteObject
  extends ZObject
  with HasRef[ZConcreteObject]

trait ZPersistedObject
  extends ZObject
  with HasRef[ZPersistedObject]
{

}

trait ZImmutableObject[A <: ImmutableAccessor]
  extends ZObject
  with HasImmutableRef[A, ZImmutableObject[A]]
{
//  def fieldSets: IFieldSetMap[A]
}

trait ZMutableObject[A <: MutableAccessor]
  extends ZObject
  with HasMutableRef[A, ZMutableObject[A]]
{

//  def fieldSets: MFieldSetMap[A]
}

trait ZObjectReference[+Trait <: ZObject]
  extends ZObject
{
//  def refT[T <: ZObject] = this.asInstanceOf[ReferenceT[T]]
//  def ref = this.asInstanceOf[ReferenceT[Trait]]

}


case class ZImmutableObjectReference
[A <: ImmutableAccessor, +Trait <: ZImmutableObject[A]] protected[model]( acc: A, id: Zid )
  extends ZObjectReference[Trait]
  with ZImmutableObject[A]
{
//  type A0 = acc.type

  def get: ZConcreteImmutableObject[acc.type] = acc.getObject(id).get

  override def refT[T <: ZImmutableObject[A]] = this.asInstanceOf[IReferenceTA[A, T]]

//  override def ref = this.asInstanceOf[IReferenceTA[A, Trait]]

  def zClass: ZClass = get.zClass

  def fieldSets: IFieldSetMap[acc.type] = get.fieldSets

}

case class ZMutableObjectReference
[A <: MutableAccessor, +Trait <: ZMutableObject[A]] protected[model] ( acc: A, id: ZIdentity)
  extends ZObjectReference[Trait]
  with ZMutableObject[A]
{

  def get: ZConcreteMutableObject[acc.type] = acc.getObject(id).get

  override def refT[T <: ZMutableObject[A]] = this.asInstanceOf[MReferenceTA[A, T]]

  def zClass: ZMClass[acc.type] = get.zClass

  def fieldSets: MFieldSetMap[acc.type] = get.fieldSets

}

/**
 * An immutable Object that corresponds to an Object in the database
 */
trait ZConcreteImmutableObject[A <: ImmutableAccessor]
  extends ZImmutableObject[A]
  with ZConcreteObject
  with ZPersistedObject
  with HasImmutableRef[A, ZConcreteImmutableObject[A]]
{
  override def zClass: ZIClass[A]

  def fieldSets: IFieldSetMap[A]

}


/**
 * An Object that can be Modified
 */
trait ZConcreteMutableObject[A <: MutableAccessor]
  extends ZMutableObject[A]
  with ZConcreteObject
  with HasMutableRef[A, ZConcreteMutableObject[A]]
{

  type T <: ZConcreteMutableObject[A]

  // Accessors
  override def zClass: ZMClass[A]

  def fieldSets: MFieldSetMap[A]

  def deleted_? : Boolean

  // Mutation

  protected def update( fieldSets: MFieldSetMap[A] = fieldSets,
                        deleted_? : Boolean = deleted_?
                        ): T

  def mutateFieldSets(mutate: ZMutableFieldSet[A] => ZMutableFieldSet[A]): T = {
    val newFieldSets = fieldSets.mapValues(mutate)
    update(fieldSets = newFieldSets)
  }
  def mutateFieldSet( role: ZMRole[A], fieldClass: ZMFieldClass[A] )
                    ( mutate: ZMutableFieldSet[A] => ZMutableFieldSet[A] ): T = {

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
trait ZModifiedObject[A <: MutableAccessor]
  extends ZConcreteMutableObject[A]
  with ZPersistedObject
  with HasMutableRef[A, ZModifiedObject[A]]
{

  override type T <: ZModifiedObject[A]

  def id: ZPersistedIdentity

}


/**
 * An Object that has not been persisted to the database
 */
trait ZNewObject[A <: MutableAccessor]
  extends ZConcreteMutableObject[A]
  with HasMutableRef[A, ZNewObject[A]]
{

  override type T <: ZNewObject[A]

  def id: TempId

}



