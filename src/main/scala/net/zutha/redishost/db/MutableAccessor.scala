package net.zutha.redishost.db

import com.redis.{RedisCommand, RedisClient}
import scala.language.implicitConversions

class MutableAccessor protected[db] (val redis: RedisClient, val dbAcc: ImmutableAccessor)
  extends Accessor
  with MutableReadQueries
  with UpdateQueries
{
  implicit def extendRedisWithUpdate( r: RedisCommand ) = new RedisUpdateExtensions(r)

  def discardChanges() { ??? }
  def commitChanges() { ??? }


}
