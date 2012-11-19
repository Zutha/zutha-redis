package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literal.Name
import net.zutha.redishost.model.companion.ZFieldClassCompanion
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.role.Namable
import net.zutha.redishost.db.MutableAccessor

object NamableHasName extends ZFieldClassCompanion[ZField, IField, MField] {

  def name = "Namable Has Name"

  def apply( namable: MRef[MItem], name: Name )
           (implicit acc: MutableAccessor ): NewField = {
    ZField( NamableHasName )( Namable.refM -> namable )( Name.refM -> name )
  }

  def validType_?(obj: ZObject) = ???
}
