package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literaltype.Name
import net.zutha.redishost.model.singleton.{ZSingleton, ZFieldClassSingleton}
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.role.Namable
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.datatype.ZString

object NameableHasName
  extends ZSingleton[ZFieldClass]
  with ZFieldClassSingleton[ZField]
{

  def name = "Namable Has Name"

  def apply( namable: MRef[ZItem], name: ZString )
           ( implicit acc: MutableAccessor ): NewPropertyField = {
    ZPropertyField( NameableHasName, Namable -> namable, Name -> name )
  }


}
