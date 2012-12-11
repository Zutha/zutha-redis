package net.zutha.redishost.model

import fieldset.{MFieldSetRef, IFieldSetRef, ZFieldSetRef}
import itemclass._
import net.zutha.redishost.db.{ImmutableAccessor, Accessor, MutableAccessor}
import net.zutha.redishost.model.MsgType._
import scala.reflect.runtime.universe._


trait ZObjectLike[+This <: ZObject]
{
  self: This =>

  type U <: ZObject
  type L = This

  // Accessors

  implicit def acc: Accessor

  def id: ZIdentity

  def ref[T >: L <: U: TypeTag]: ZRef[T]

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

  // Comparison

  def sameAs(other: ZObject): Boolean = id == other.id

  // Querying

  def reload[T >: L <: U: TypeTag]: T
}

trait IObjectLike[+This <: IObject]
  extends ZObjectLike[This]
{
  self: This =>

  type U = IObject


  // Accessors

  implicit def acc: ImmutableAccessor

  def id: Zids

  def ref[T >: L <: IObject: TypeTag] = IRef[T]( id )

  def zid: Zid = id.zid

  override def zClass: IRef[IClass]

  def fieldSets: Seq[IFieldSetRef]

  // Queries

  def reload[T >: L <: IObject: TypeTag]: T = acc.reloadObject( this.ref[T] )

  def hasType_? ( zType: IRef[IType] ) : Boolean = acc.objectHasType( this.ref, zType )

}

trait MObjectLike[+This <: MObject]
  extends ZObjectLike[This]
{
  self: This =>

  type U = MObject

  // Accessors

  implicit def acc: MutableAccessor

  def id: ZIdentity

  def ref[T >: L <: MObject: TypeTag] = MRef[T]( id )

  override def zClass: MRef[MClass]

  def fieldSets: Seq[MFieldSetRef]

  def deleted_? : Boolean

  def messages: Seq[(MsgType, String)]

  // Queries

  def reload[T >: L <: MObject: TypeTag]: T = acc.reloadObject( this.ref[T] )

  def hasType_? ( zType: MRef[MType] ) : Boolean = acc.objectHasType( this.ref, zType )

  def persisted_? = primaryZids.size > 0

  def merged_? = primaryZids.size >= 2

  // Mutation

  def delete[T >: L <: MObject: TypeTag]: T = {
    acc.deleteObject( this.ref )
    reload
  }

  def restore[T >: L <: MObject: TypeTag]: T = {
    acc.restoreObject( this.ref )
    reload
  }
}
