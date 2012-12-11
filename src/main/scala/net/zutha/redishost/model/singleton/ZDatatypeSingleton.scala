package net.zutha.redishost.model.singleton

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.fieldmember.LiteralValue

protected[redishost] trait ZDatatypeSingleton[+V <: LiteralValue]
  extends ZTypeSingleton
  with ZItemSingleton[ZDatatype]
{

  def make( value: String ): Option[V]

  def validate( value: String ): Boolean = make( value ) match {
    case Some(_) => true
    case None => false
  }

}
