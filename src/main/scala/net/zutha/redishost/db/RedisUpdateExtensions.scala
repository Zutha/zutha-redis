package net.zutha.redishost.db

import com.redis.RedisCommand
import net.zutha.redishost.model._
import fieldclass._
import fieldset._
import itemclass._

protected[db] class RedisUpdateExtensions( r: RedisCommand ) extends RedisKeys {

  def setObjectClass( objId: String, classId: String ) {
    r.hset( objHashKey(objId), "class", classId )
    r.sadd( classInstancesKey(classId), objId )
  }

  def addTypeToObject( objId: String, typeId: String ) {
    r.sadd( objDirectTypesKey(objId), typeId )
    r.sunionstore( objAllTypesKey(objId), objAllTypesKey(objId), typeAllSupertypesKey(typeId) )
  }

  def setFieldScope( fieldId: String, scope: MScopeMap ) {
    scope foreach {s =>
      val key = fieldScopesKey(fieldId, s._1.key)
      val scopeItems: Seq[String] = s._2.map(_.key).toSeq
      r.del(key)
      if ( scopeItems.size > 0 ){
        r.sadd( key, scopeItems.head, scopeItems.tail:_* )
      }
    }
  }

  def addRolePlayersToField( fieldId: String, rolePlayers: MRolePlayerMap ) {
    rolePlayers foreach { case (role, players) =>
      val key = fieldRolePlayersKey( fieldId, role.key )
      val playerIds = players.map(_.key)
      if ( playerIds.size > 0 ){
        r.sadd( key, playerIds.head, playerIds.tail )
      }
    }
  }

  def addLiteralsToField( fieldId: String, literals: MLiteralMap ) {
    literals foreach { case (literalType, players) =>
      val key = fieldLiteralsKey( fieldId, literalType.key )
      val literalStrings = players.map(_.toString)
      if ( literalStrings.size > 0 ){
        r.sadd( key, literalStrings.head, literalStrings.tail )
      }
    }
  }

  // ----- Literals Index -----

  def indexAddName( name: String, objKey: String ) {
    r.hset( nameHashKey, name, objKey )
  }
}
