package net.zutha.redishost.model

import net.zutha.redishost.db.Accessor
import itemclass._
import scala.reflect.runtime.universe._
import net.zutha.redishost.exception.DanglingRefException

case class Ref
[+U <: ZObject, +T <: U] private[redishost]( key: String )
                                               ( implicit private[this] val acc: Accessor[U],
                                                 private[this] val tt: TypeTag[T] )
{

  private def loadUnsafe: T = {
    acc.getObjectByKey( key ).getOrElse(
      throw new DanglingRefException( this )
    ).asInstanceOf[T]
  }

  def load: T = {
    // TODO check for this ref's type being wrong (not conforming to this ZObject's ZObjectType)
    loadUnsafe
  }

}

