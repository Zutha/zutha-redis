package net.zutha.redishost.db

import com.redis.RedisClient

class ImmutableAccessor protected[db] ( protected[db] val redis: RedisClient )
  extends Accessor[ImmutableAccessor]
  with ImmutableReadQueries
{

}
