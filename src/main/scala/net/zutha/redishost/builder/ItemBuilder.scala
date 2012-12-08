package net.zutha.redishost.builder

import net.zutha.redishost.model.fieldclass.{NewPropertyField, NamableHasName}
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MItem
import net.zutha.redishost.model.datatype.ZString

trait ItemBuilder
  extends ObjectBuilder
  with Builder[ItemBuilder]
{
  def ref: MRef[MItem]

  def name( name: ZString ): MRef[NewPropertyField] = {
    NamableHasName( ref, name ).ref
  }
}
