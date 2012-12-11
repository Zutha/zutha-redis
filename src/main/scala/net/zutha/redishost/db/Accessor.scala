package net.zutha.redishost.db

import com.redis.RedisClient
import scala.language.implicitConversions
import itemclass.ZObject

trait Accessor[+U <: ZObject]
  extends ReadQueries[U]
  with RedisKeys
  with RedisParsers
{
  protected def redis: RedisClient

  implicit def extendRedisWithRead( r: RedisClient ) = new RedisReadExtensions(r)
}
