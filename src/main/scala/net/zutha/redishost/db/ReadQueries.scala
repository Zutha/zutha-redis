package net.zutha.redishost.db

import com.redis.RedisClient
import net.zutha.redishost.model._

trait ReadQueries extends Queries {
  type A0 <: Accessor
  def me: A0

  def getObjectRaw(id: Zid, fieldLimit: Int = 0): Option[ZConcreteObject]

}
