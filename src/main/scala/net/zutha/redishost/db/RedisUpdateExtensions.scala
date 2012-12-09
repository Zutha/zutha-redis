package net.zutha.redishost.db

import com.redis.RedisCommand
import net.zutha.redishost.model._
import fieldmember._

protected[db] class RedisUpdateExtensions( r: RedisCommand ) extends RedisKeys {

  def specifyObjectClass( objId: String, classId: String ) {
    r.hset( objHashKey(objId), "class", classId )
    r.sadd( classInstancesKey(classId), objId )
    addTypeToObject( objId, classId )
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

  def addRolePlayersToField( fieldId: String, rolePlayers: Set[MRolePlayer] ) {
    rolePlayers.groupBy(_.role) foreach { case (role, rps) =>
      val key = fieldRolePlayersKey( fieldId, role.key )
      val playerIds = rps.map(_.player.key)
      if ( playerIds.size > 0 ){
        r.sadd( key, playerIds.head, playerIds.tail )
      }
    }
  }

  def setFieldLiterals( fieldId: String, literals: MLiteralMap ) {
    val kvs = literals.toSeq map { case Pair( literalType, value ) =>
      val key = fieldLiteralsKey( fieldId, literalType.key )
      ( key -> value.toString )
    }
    r.mset( kvs:_* )
  }

  // ----- Literals Index -----

  def indexAddName( name: String, objKey: String ) {
    r.hset( nameHashKey, name, objKey )
  }
}
