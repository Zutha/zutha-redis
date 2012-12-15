package net.zutha.redishost.builder

import net.zutha.redishost.model.fieldclass.{ZField, NameableHasName}
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.datatype.ZString
import net.zutha.redishost.model.itemclass.ZItem

trait ItemBuilder
  extends ObjectBuilder
  with Builder[ItemBuilder]
{
  def ref: MRef[ZItem]

  def name( name: ZString ): MRef[ZField] = {
    NameableHasName( ref, name ).zRef
  }
}
