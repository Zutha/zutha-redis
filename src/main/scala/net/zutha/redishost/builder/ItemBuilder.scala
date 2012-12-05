package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.{NewPropertyField, NewBinaryField, NamableHasName}
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MItem
import net.zutha.redishost.model.literal.Name

trait ItemBuilder
  extends ObjectBuilder
  with Builder[ItemBuilder]
{
  def ref: MRef[MItem]

  def name( name: String ): MRef[NewPropertyField] = {
    NamableHasName( ref, Name(name)).ref
  }
}
