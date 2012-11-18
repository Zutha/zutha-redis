package net.zutha.redishost.db

import net.zutha.redishost.model._
import net.zutha.redishost.model.TempId
import net.zutha.redishost.model.NewField
import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.NewItem

trait UpdateQueries { self: MutableAccessor =>

  /**
   * create a new object with a tempId
   * @return the tempId of the new object
   */
  protected[db] def createNewObject: String = {
    redis.evalBulk[String]( Lua("create_temp_object"), List( idCounterKey ), List( OBJ_PREFIX, objIsNewHKey )).get
  }

  protected[redishost] def createSchemaRef( name: String ): MRef[MObject] = {
    val newId = createNewObject
    redis.indexAddName( name, newId )
    MRef(this, TempId(newId))
  }

  def createItem( zClass: MRef[MItemClass] ): NewItem = {
    val newId = createNewObject
    redis.pipeline {r =>
      r.setObjectClass( newId, zClass.key )
      r.addTypeToObject( newId, zClass.key )
    }
    NewItem( this, TempId(newId), zClass, List(), List() )
  }

  def createField( zClass: MRef[MFieldClass],
                   rolePlayers: MRolePlayerMap,
                   literals: MLiteralMap,
                   scope: MScopeMap
  ): NewField = {
    val newId = createNewObject

    redis.pipeline {r =>
      r.setObjectClass( newId, zClass.key )
      r.addTypeToObject( newId, zClass.key )
      r.addRolePlayersToField( newId, rolePlayers )
      r.addLiteralsToField( newId, literals )
      r.setFieldScope( newId, scope )
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
