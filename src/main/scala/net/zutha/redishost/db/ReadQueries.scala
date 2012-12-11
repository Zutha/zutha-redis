package net.zutha.redishost.db

import net.zutha.redishost.model._
import datatype.{URI, ZString}
import fieldclass.ZField
import itemclass._
import literaltype.Name

trait ReadQueries { self: Accessor =>

  def lookupObjectKeyByPSI( psi: URI ): Option[String] = redis.hget( psiHashKey, psi.asString )

  def lookupObjectKeyByName( name: ZString ): Option[String] = redis.hget( nameHashKey, Name.indexForm(name) )

  def getObjectZids( objKey: String ): Set[Zid] = {
    redis.smembers[Zid]( objZidsKey(objKey) ).get.map(_.get)
  }

  def getObjectRef( objKey: String ): Option[ZRef[ZObject]]

  def getObjectByKey( key: String): Option[ZObject]

  def getItemByKey( key: String ): Option[ZItem]

  def getFieldByKey( key: String ): Option[ZField]
}
