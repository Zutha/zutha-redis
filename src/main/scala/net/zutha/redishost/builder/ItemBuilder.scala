package net.zutha.redishost.builder

import net.zutha.redishost.db.MutableAccessor

trait ItemBuilder {
  implicit def acc: MutableAccessor

  def name( name: String ) {
    ???
  }
}
