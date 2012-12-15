package net.zutha.redishost.model.singleton

import net.zutha.redishost.model._
import fieldmember.{LiteralFieldMember, LiteralValue}
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.Accessor


protected[redishost] trait ZLiteralTypeSingleton[V <: LiteralValue]
  extends ZTypeSingleton
  with ZSingletonLike[ZLiteralType]
{
  self: ZSingleton[ZLiteralType] =>

  protected def datatypeCompanion: ZDatatypeSingleton[V]

  def makeLiteral[A <: Accessor[A]]( value: V )( implicit acc: A ): LiteralFieldMember[A] = LiteralFieldMember( this.ref[A, ZLiteralType], value )
  def -> [A <: Accessor[A]] ( value: V )( implicit acc: A ): LiteralFieldMember[A] = makeLiteral( value )
}
