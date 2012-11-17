package net.zutha.redishost.db

import net.zutha.redishost.model._
import com.redis.{RedisCommand}

protected[db] trait RedisUpdateQueries extends RedisQueries {

  // Note: all these methods should be protected[db].
  // They should only be used by an Accessor to commit changes to its database
  // An ImmutableAccessor will only ever receive update instructions from a MutableAccessor


  def setObjectClass( r: RedisCommand, itemId: String, classId: String ) {
    r.hset( objKey(itemId), "class", objKey(classId) )
  }

  def addTypeToObject( r: RedisCommand, objId: String, typeId: String ) {
    r.sadd( objDirectTypesKey(objId), typeId )
    r.sunionstore( objAllTypesKey(objId), objAllTypesKey(objId), typeAllSupertypesKey(typeId) )
    r.sadd( typeInstancesKey(typeId), objId )
  }

  def setFieldScope( r: RedisCommand, fieldId: String, scope: MScopeMap ) {
    scope foreach {s =>
      val key = fieldScopesKey(fieldId, s._1.key)
      val scopeItems: Seq[String] = s._2.map(_.key).toSeq
      r.del(key)
      if ( scopeItems.size > 0 ){
        r.sadd( key, scopeItems.head, scopeItems.tail:_* )
      }
    }
  }

  def addRolePlayersToField( r: RedisCommand, fieldId: String, rolePlayers: MRolePlayerMap ) {
    rolePlayers foreach { case (role, players) =>
      val key = fieldRolePlayersKey( fieldId, role.key )
      val playerIds = players.map(_.key)
      if ( playerIds.size > 0 ){
        r.sadd( key, playerIds.head, playerIds.tail )
      }
    }
  }

  def addLiteralsToField( r: RedisCommand, fieldId: String, literals: MLiteralMap ) {
    literals foreach { case (literalType, players) =>
      val key = fieldLiteralsKey( fieldId, literalType.key )
      val literalStrings = players.map(_.toString)
      if ( literalStrings.size > 0 ){
        r.sadd( key, literalStrings.head, literalStrings.tail )
      }
    }
  }
}
