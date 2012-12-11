package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import fieldmember.{LiteralValue, MLiteral, ILiteral}
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}



protected[redishost] trait ZLiteralTypeSingleton[V <: LiteralValue]
  extends ZTypeSingleton
  with ZItemSingleton[ZLiteralType]
{
  protected def datatypeCompanion: ZDatatypeSingleton[V]

  val ref: MRef[ZLiteralType] = this.ref
  def makeILiteral( value: V )( implicit acc: ImmutableAccessor ): ILiteral = ILiteral( this.refI, value )

  def makeMLiteral( value: V )( implicit acc: MutableAccessor ): MLiteral = MLiteral( this.ref, value )
  def -> ( value: V )( implicit acc: MutableAccessor ): MLiteral = makeMLiteral( value )
}
