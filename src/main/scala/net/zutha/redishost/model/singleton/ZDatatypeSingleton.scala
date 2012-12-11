package net.zutha.redishost.model.singleton

import fieldmember.LiteralValue
import net.zutha.redishost.model.itemclass._

protected[redishost] trait ZDatatypeSingleton[+V <: LiteralValue]
  extends ZTypeSingleton
  with ZItemSingleton[ZDatatype, IDatatype, MDatatype]
{

  def make( value: String ): Option[V]

  def validate( value: String ): Boolean = make( value ) match {
    case Some(_) => true
    case None => false
  }

}
