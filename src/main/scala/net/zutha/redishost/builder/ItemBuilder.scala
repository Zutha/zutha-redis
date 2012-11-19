package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.NamableHasName
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MItem
import net.zutha.redishost.model.literal.Name

trait ItemBuilder {
  implicit def acc: MutableAccessor

  def ref: MRef[MItem]

  def name( name: String ) {
    NamableHasName( ref, Name(name))
  }
}
