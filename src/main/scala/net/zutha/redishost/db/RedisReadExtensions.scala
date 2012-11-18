package net.zutha.redishost.db

import com.redis.RedisClient
import net.zutha.redishost.model.Zid

protected[db] class RedisReadExtensions( r: RedisClient )
  extends RedisKeys
  with RedisParsers
{



}
