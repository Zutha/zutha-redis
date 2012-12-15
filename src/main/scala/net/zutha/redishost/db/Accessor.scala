package net.zutha.redishost.db

import com.redis.RedisClient

trait Accessor[A <: Accessor[A]]
  extends ReadQueries[A]
  with RedisKeys
  with RedisParsers
{
  self: A =>

  implicit protected val acc = this

  protected[db] def redis: RedisClient

  import scala.language.implicitConversions
  protected[db] implicit def extendRedisWithRead( r: RedisClient ) = new RedisReadExtensions(r)
}
