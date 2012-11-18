package net.zutha.redishost.db

import com.redis.RedisClient
import net.zutha.redishost.model.{IObject, Zid}

class ImmutableAccessor protected[db] (protected val redis: RedisClient)
  extends Accessor
  with ImmutableReadQueries
{
  implicit def acc = this

}
