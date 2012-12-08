package net.zutha.redishost.model.fieldclass

import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.literaltype.Name
import net.zutha.redishost.model.companion.ZFieldClassCompanion
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.role.Namable
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.datatype.ZString

object NamableHasName extends ZFieldClassCompanion[ZField, IField, MField] {

  def name = "Namable Has Name"

  def apply( namable: MRef[MItem], name: ZString )
           (implicit acc: MutableAccessor ): NewPropertyField = {
    ZField( NamableHasName, Namable.refM -> namable, Name -> name )
  }


}
