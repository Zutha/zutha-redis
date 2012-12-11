package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import itemclass._
import scala.reflect.runtime.universe._

trait ZRef[+T <: ZObject]
{
  def load: T

  def id: ZIdentity

  def key: String = id.key
}

final case class IRef[+T <: IObject] private[redishost]( id: Zids )( implicit val acc: ImmutableAccessor,
                                                                     implicit private[this] val tt: TypeTag[T] )
  extends ZRef[T]
{

  def load: T = acc.reloadObject(this)

  def zid: Zid = id.zid
}

final case class MRef[+T <: MObject] private[redishost] ( id: ZIdentity)( implicit val acc: MutableAccessor,
                                                                          implicit private[this] val tt: TypeTag[T] )
  extends ZRef[T]
{
  def load: T = acc.reloadObject(this)
}
