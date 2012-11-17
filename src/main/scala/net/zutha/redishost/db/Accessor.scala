package net.zutha.redishost.db

import com.redis.RedisClient

trait Accessor extends ReadQueries {
  protected def redis: RedisClient
}
