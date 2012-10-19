package net.zutha.redishost.db

import net.zutha.redishost.model.{MObject, ZIdentity}
import collection.parallel.mutable.ParMap
import com.redis.RedisClient

class MutableAccessor protected[db] (val redis: RedisClient, val dbAcc: ImmutableAccessor)
  extends Accessor
  with MutableReadQueries
  with RedisReadQueries
  with RedisUpdateQueries
{
  type A0 = this.type
  def me = this


  def update[A >: A0 <: MutableAccessor with Singleton](id: ZIdentity, o: MObject[A]): Boolean = {
    // save object in private redis key domain for this accessor
    ???
  }

  def discardChanges() { ??? }
  def commitChanges() { ??? }


}
