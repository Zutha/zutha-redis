package net.zutha.redishost.db

import com.redis.RedisClient
import net.zutha.redishost.model.{IObject, Zid}

class ImmutableAccessor protected[db] (r: RedisClient)
  extends Accessor
  with ImmutableReadQueries
{
  protected def redis = r

}
