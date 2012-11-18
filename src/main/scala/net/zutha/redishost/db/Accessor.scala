package net.zutha.redishost.db

import com.redis.RedisClient
import scala.language.implicitConversions

trait Accessor
  extends ReadQueries
  with RedisKeys
  with RedisParsers
{
  protected def redis: RedisClient

  implicit def extendRedisWithRead( r: RedisClient ) = new RedisReadExtensions(r)
}
