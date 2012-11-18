package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}


trait ZRef[+T <: ZObject]
{
  def get: T

  def id: ZIdentity

  def key: String = id.key
}

case class IRef[+T <: IObject] protected[redishost]( id: Zids )( implicit val acc: ImmutableAccessor )
  extends ZRef[T]
{

  def get: T = acc.getObjectT(id.zid).get

  def zid: Zid = id.zid
}

case class MRef[+T <: MObject] protected[redishost] ( id: ZIdentity)( implicit val acc: MutableAccessor )
  extends ZRef[T]
{
  def get: T = acc.getObjectT(id).get
}
