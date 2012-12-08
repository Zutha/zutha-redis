package net.zutha.redishost.model.companion

import net.zutha.redishost.model._
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.literal.{LiteralValue, MLiteral, ILiteral}


protected[redishost] trait ZLiteralTypeCompanion[V <: LiteralValue]
  extends SchemaItem
{
  type ObjC = ZLiteralType
  type ObjCM = MLiteralType
  type ObjCI = ILiteralType

  type ObjT = ZLiteralType
  type ObjTM = MLiteralType
  type ObjTI = ILiteralType

  protected def classFactory = ZLiteralType

  protected def datatypeCompanion: ZDatatypeCompanion[V]

  def makeILiteral( value: V )( implicit acc: ImmutableAccessor ): ILiteral = ILiteral( this.refI, value )

  def makeMLiteral( value: V )( implicit acc: MutableAccessor ): MLiteral = MLiteral( this.refM, value )
  def -> ( value: V )( implicit acc: MutableAccessor ): MLiteral = makeMLiteral( value )
}