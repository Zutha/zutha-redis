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
  def ref: ZRef[T]

  def id: ZIdentity
  def zClass: ZRef[ZClass]
  def fieldSets: List[ZFieldSetRef]

  def zids: List[Zid] = id match {
    case TempId(_) => List()
    case MZids(_, allZids) => allZids
    case Zids(zid, allZids) => allZids
  }
  def primaryZids: List[Zid] = id match {
    case TempId(_) => List()
    case MZids(pZids, _) => pZids
    case Zids(zid, allZids) => List(zid)
  }

  def persisted_? = primaryZids.size > 0

  def merged_? = primaryZids.size >= 2

  // Comparison

  def sameAs(other: ZObject): Boolean = id == other.id


}

trait PersistedObject
  extends ZObject
{
	type T <: PersistedObject

  def id: PersistedId
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

  def id: Zids

  def zid: Zid = id.zid

  override def zClass: IRef[IClass]

  def fieldSets: List[IFieldSetRef]

}


/**
 * An Object that can be Modified
 */
trait MObject
  extends ZObject
  with HasMRef
{
	type T <: MObject

  // Accessors
  def acc: MutableAccessor

  def id: ZIdentity

  override def zClass: MRef[MClass]

  def fieldSets: List[MFieldSetRef]

  def deleted_? : Boolean

  // Mutation

  protected def update( fieldSets: List[MFieldSetRef] = fieldSets,
                        deleted_? : Boolean = deleted_?
                        ): T

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

  def id: PersistedId

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



