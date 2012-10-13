package net.zutha.redishost.db

import com.redis.RedisClient

object DB extends UpdateQueries with ReadQueries {
  val redis = new RedisClient()

}
