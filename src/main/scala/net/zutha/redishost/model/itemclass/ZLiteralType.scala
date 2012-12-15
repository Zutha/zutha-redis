package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.{ZRoleSingleton, ZSingleton, ZItemClassSingleton}
import net.zutha.redishost.model.fieldmember._
import net.zutha.redishost.model.Referenceable

object ZLiteralType
  extends ZSingleton[ZItemClass with ZRole]
  with ZItemClassSingleton[ZLiteralType]
  with ZRoleSingleton
{

  def name = "LiteralType"
}

trait ZLiteralType
  extends ZFieldMemberType
  with Referenceable[ZLiteralType]
{
  def makeLiteral( value: LiteralValue )( implicit acc: A ): LiteralFieldMember[A] = LiteralFieldMember( this.zRef, value )
  def -> ( value: LiteralValue )( implicit acc: A ): LiteralFieldMember[A] = makeLiteral( value )

  def datatype: ZDatatype

}
