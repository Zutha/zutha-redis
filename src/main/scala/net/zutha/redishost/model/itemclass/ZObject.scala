package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._
import companion.ZClassCompanion
import fieldset._
import MsgType._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor, Accessor}

object ZObject extends ZClassCompanion[ZObject, IObject, MObject] {
  type ObjC = ZClass
  type ObjCI = IClass
  type ObjCM = MClass

  type ObjT = ZClass with ZRole
  type ObjTM = MClass with MRole
  type ObjTI = IClass with IRole

  protected def classFactory = ZClass

  def name = "Object"


}


trait ZObject
{
  type T <: ZObject

  // Accessors
  implicit def acc: Accessor

  def ref: ZRef[T]

  def id: ZIdentity
  def key: String = id.key
  def zClass: ZRef[ZClass]
  def fieldSets: Seq[ZFieldSetRef]

  def zids: Seq[Zid] = id match {
    case TempId(_) => Seq()
    case MZids(_, allZids) => allZids
    case Zids(zid, allZids) => allZids
  }
  def primaryZids: Seq[Zid] = id match {
    case TempId(_) => Seq()
    case MZids(pZids, _) => pZids
    case Zids(zid, allZids) => Seq(zid)
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
{
	type T <: IObject

  // Accessors
  implicit def acc: ImmutableAccessor

  def ref: IRef[T] = IRef[T]( id )

  def id: Zids

  def zid: Zid = id.zid

  override def zClass: IRef[IClass]

  def fieldSets: Seq[IFieldSetRef]

  // Queries

  def hasType_? ( zType: IRef[IType] ) : Boolean = acc.objHasType( zType )

}


/**
 * An Object that can be Modified
 */
trait MObject
  extends ZObject
{
	type T <: MObject

  // Accessors

  implicit def acc: MutableAccessor

  def ref: MRef[T] = MRef[T]( id )

  def id: ZIdentity

  override def zClass: MRef[MClass]

  def fieldSets: Seq[MFieldSetRef]

  def deleted_? : Boolean

  def messages: Seq[(MsgType, String)]

  // Queries

  def hasType_? ( zType: MRef[MType] ) : Boolean = acc.objHasType( zType )

  // Mutation

  protected def update( fieldSets: Seq[MFieldSetRef] = fieldSets,
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



