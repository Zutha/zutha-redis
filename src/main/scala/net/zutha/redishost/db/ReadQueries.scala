package net.zutha.redishost.db

import net.zutha.redishost.model._
import itemclass._

trait ReadQueries { self: Accessor =>

  def lookupObjectIdByPSI( psi: String ): Option[String] = redis.hget( psiHashKey, psi )

  def lookupObjectIdByName( name: String ): Option[String] = redis.hget( nameHashKey, name )

  def getObjectZids( objKey: String ): Set[Zid] = {
    redis.smembers[Zid]( objZidsKey(objKey) ).get.map(_.get)
  }

  def getObjectRef( objKey: String ): Option[ZRef[ZObject]]

  def getObjectRaw( objKey: String, fieldLimit: Int = 0 ): Option[ZObject]

}
