package net.zutha.redishost.db

import com.redis.RedisClient
import net.zutha.redishost.model.{ZConcreteImmutableObject, Zid}

class ImmutableAccessor protected[db] (r: RedisClient)
  extends Accessor
  with ImmutableReadQueries
  with RedisReadQueries
  with RedisUpdateQueries
{
  type A0 = this.type
  def me = this

  protected def redis = r

}
