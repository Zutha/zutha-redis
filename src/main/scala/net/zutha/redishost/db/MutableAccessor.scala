package net.zutha.redishost.db

import com.redis.{RedisCommand, RedisClient}
import scala.language.implicitConversions

class MutableAccessor protected[db] ( protected[db] val redis: RedisClient, protected[db] val dbAcc: ImmutableAccessor)
  extends Accessor[MutableAccessor]
  with MutableReadQueries
  with UpdateQueries
{
  implicit def extendRedisWithUpdate( r: RedisCommand ) = new RedisUpdateExtensions(r)

  def discardChanges() { ??? }
  def commitChanges() { ??? }


}
