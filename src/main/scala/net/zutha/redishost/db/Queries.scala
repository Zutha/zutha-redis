package net.zutha.redishost.db

import com.redis.RedisClient

trait Queries {
  protected def redis: RedisClient
}
