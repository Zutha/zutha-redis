package net.zutha.redishost.db

import net.zutha.redishost.model.{ZObject, ZIdentity}

trait Accessor extends ReadQueries {

  def hashCode: Int
}
