package net.zutha.redishost.model

import itemclass.ZObject
import net.zutha.redishost.db.Accessor
import special.ZNothing
import scala.reflect.runtime.universe._
import net.zutha.redishost.exception.ZTypeChangedException


trait Referenceable[+L >: ZNothing <: ZObject] {

  type A <: Accessor[A]

  implicit def acc: A

  def zKey: String

  def zRef[T >: L <: ZObject : TypeTag]: ZRef[A, T] = acc.getTypedRef( zKey ).getOrElse(
    throw new ZTypeChangedException
  )

}