package net.zutha.redishost.model

import fieldset.{MFieldSetRef, IFieldSetRef, ZFieldSetRef}
import itemclass._
import net.zutha.redishost.model.MsgType._


private[model]
trait ZObjectLike
  extends Loadable[ZObject, ZObject]
{
  self: ZObject =>

  // Accessors

  def id: ZIdentity

  def key: String = id.key

  def zClass: ZRef[A, ZClass]

  def fieldSets: Seq[ZFieldSetRef[A]]
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

  def sameAs( other: ZObject ): Boolean = id == other.id

  // Querying

}

private[model]
trait IObjectLike
  extends ZObjectLike
  with Accessible[IA]
  with Loadable[IObject, ZObject]
{
  self: IObject =>

  // Accessors

  def id: Zids

  def zid: Zid = id.zid

  override def zClass: IRef[ZClass]

  def fieldSets: Seq[IFieldSetRef]

  // Queries

  def hasType_? ( objType: IRef[ZObjectType] ) : Boolean = acc.objectHasType( this.zRef, objType )

}

private[model]
trait MObjectLike
  extends ZObjectLike
  with Accessible[MA]
  with Loadable[MObject, ZObject]
{
  self: MObject =>

  // Accessors

  def id: ZIdentity

  override def zClass: MRef[ZClass]

  def fieldSets: Seq[MFieldSetRef]

  def deleted_? : Boolean

  def messages: Seq[(MsgType, String)]

  // Queries

  def hasType_? ( objType: MRef[ZObjectType] ) : Boolean = acc.objectHasType( this.zRef, objType )

  def persisted_? = primaryZids.size > 0

  def merged_? = primaryZids.size >= 2

  // Mutation

  def delete: Impl = {
    acc.deleteObject( this.zRef )
    reload
  }

  def restore: Impl = {
    acc.restoreObject( this.zRef )
    reload
  }
}
