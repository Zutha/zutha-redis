package net.zutha.redishost.db

import net.zutha.redishost.model._
import datatype.{URI, ZString}
import itemclass._
import literaltype.Name

trait ReadQueries { self: Accessor =>

  def lookupObjectIdByPSI( psi: URI ): Option[String] = redis.hget( psiHashKey, psi.asString )

  def lookupObjectIdByName( name: ZString ): Option[String] = redis.hget( nameHashKey, Name.indexForm(name) )

  def getObjectZids( objKey: String ): Set[Zid] = {
    redis.smembers[Zid]( objZidsKey(objKey) ).get.map(_.get)
  }

  def getObjectRef( objKey: String ): Option[ZRef[ZObject]]

  def getObjectRaw( objKey: String, fieldLimit: Int = 0 ): Option[ZObject]

}
