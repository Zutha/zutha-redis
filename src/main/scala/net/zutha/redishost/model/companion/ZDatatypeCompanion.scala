package net.zutha.redishost.model.companion

import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._
import scala.Some
import net.zutha.redishost.literal.LiteralValue

protected[redishost] trait ZDatatypeCompanion[+V <: LiteralValue]
  extends SchemaItem
{
  type ObjC = ZDatatype
  type ObjCM = MDatatype
  type ObjCI = IDatatype

  type ObjT = ZDatatype
  type ObjTI = IDatatype
  type ObjTM = MDatatype

  protected def classFactory = ZDatatype

  def make( value: String ): Option[V]

  def validate( value: String ): Boolean = make( value ) match {
    case Some(_) => true
    case None => false
  }

}
