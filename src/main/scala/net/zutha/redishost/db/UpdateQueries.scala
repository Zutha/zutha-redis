package net.zutha.redishost.db

import net.zutha.redishost.model._
import net.zutha.redishost.model.TempId
import net.zutha.redishost.model.NewField
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.NewItem

trait UpdateQueries extends Queries { self: MutableAccessor =>

  def createItem( zClass: MRef[MItemClass] ): NewItem = {
    val newId = nextId
    redis.pipeline {r =>
      setObjectClass( r, newId, zClass.key )
      addTypeToObject( r, newId, zClass.key )
    }
    NewItem( this, TempId(newId), zClass, List(), List() )
  }

  def createField( zClass: MRef[MFieldClass],
                   rolePlayers: MRolePlayerMap,
                   literals: MLiteralMap,
                   scope: MScopeMap
  ): NewField = {
    val newId = nextId

    redis.pipeline {r =>
      setObjectClass( r, newId, zClass.key )
      addTypeToObject( r, newId, zClass.key )
      addRolePlayersToField( r, newId, rolePlayers )
      addLiteralsToField( r, newId, literals )
      setFieldScope( r, newId, scope )
    }

    val literalMembers = literals.mapValues(_.toList).map{ case (lType, values) =>
      MLiteralFieldMember(lType, values)
    }.toList
    val roleMembers = rolePlayers.mapValues(_.toList).map{case (role, players) =>
      MRoleFieldMember(role, players)
    }.toList
    val members: List[MFieldMember] = roleMembers ++ literalMembers
    val scopeList = scope.mapValues(_.toList).toList
    NewField( this, TempId(newId), zClass, List(), members, scopeList)

  }
}
