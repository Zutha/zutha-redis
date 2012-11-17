package net.zutha.redishost.db

import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits._


class MutableAccessor protected[db] (val redis: RedisClient, val dbAcc: ImmutableAccessor)
  extends Accessor
  with MutableReadQueries
  with UpdateQueries
  with RedisReadQueries
  with RedisUpdateQueries
{

  private def lastId: String = redis.get("lastId").getOrElse("0")

  def tempObjectsCount: Int = redis.get[Int]("lastId").getOrElse(0)

  def nextId: String = {
    redis.incr("lastId")
    lastId
  }

  def discardChanges() { ??? }
  def commitChanges() { ??? }


}
