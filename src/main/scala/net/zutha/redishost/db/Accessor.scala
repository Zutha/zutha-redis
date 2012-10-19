package net.zutha.redishost.db

import net.zutha.redishost.model.{ZConcreteObject, ZIdentity}

trait Accessor extends ReadQueries {

  def hashCode: Int
}
