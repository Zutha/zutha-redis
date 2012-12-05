package net.zutha.redishost.db

import com.redis.RedisClient

object DB {

  def getMutableAccessor: MutableAccessor = {
    val privateRedis = new RedisClient()
    privateRedis.select(1) //TODO return a new empty database for each call
    new MutableAccessor(privateRedis, getImmutableAccessor)
  }
  def getImmutableAccessor: ImmutableAccessor = {
    val redis = new RedisClient()
    redis.select(0)
    new ImmutableAccessor(redis)
  }
}
