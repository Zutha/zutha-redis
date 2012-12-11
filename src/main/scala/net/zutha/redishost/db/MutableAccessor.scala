package net.zutha.redishost.db

import com.redis.{RedisCommand, RedisClient}
import scala.language.implicitConversions
import net.zutha.redishost.model._

class MutableAccessor protected[db] (protected val redis: RedisClient, protected val dbAcc: ImmutableAccessor)
  extends Accessor[MObject]
  with MutableReadQueries
  with UpdateQueries
{
  implicit def acc = this

  implicit def extendRedisWithUpdate( r: RedisCommand ) = new RedisUpdateExtensions(r)

  def discardChanges() { ??? }
  def commitChanges() { ??? }


}
