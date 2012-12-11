package net.zutha.redishost.db

import com.redis.RedisClient
import net.zutha.redishost.model._

class ImmutableAccessor protected[db] (protected val redis: RedisClient)
  extends Accessor[IObject]
  with ImmutableReadQueries
{
  implicit def acc = this

}
