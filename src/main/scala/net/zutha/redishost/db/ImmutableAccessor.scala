package net.zutha.redishost.db

import com.redis.RedisClient

class ImmutableAccessor protected[db] (protected val redis: RedisClient)
  extends Accessor
  with ImmutableReadQueries
{
  implicit def acc = this

}
