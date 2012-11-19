package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldset._
import itemclass._
import literal.Name

trait UpdateQueries { self: MutableAccessor =>

  /**
   * create a new object with a tempId
   * @return the tempId of the new object
   */
  protected[db] def createNewObject: String = {
    redis.evalBulk[String]( Lua("create_temp_object"), List( idCounterKey ), List( OBJ_PREFIX, objIsNewHKey )).get
  }

  protected[redishost] def createSchemaRef( name: Name ): MRef[MObject] = {
    val newId = createNewObject
    redis.indexAddName( name.value, newId )
    MRef(TempId(newId))
  }

  def createItem( zClass: MRef[MItemClass] ): NewItem = {
    val newId = createNewObject
    redis.pipeline {r =>
      r.setObjectClass( newId, zClass.key )
      r.addTypeToObject( newId, zClass.key )
    }
    NewItem( TempId(newId), zClass, Seq(), Seq() )
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

    val literalMembers = literals.mapValues(_.toSeq).map{ case (lType, values) =>
      MLiteralFieldMember(lType, values)
    }.toSeq
    val roleMembers = rolePlayers.mapValues(_.toSeq).map{case (role, players) =>
      MRoleFieldMember(role, players)
    }.toSeq
    val members: Seq[MFieldMember] = roleMembers ++ literalMembers
    val scopeSeq = scope.mapValues(_.toSeq).toSeq
    NewField( TempId(newId), zClass, Seq(), members, scopeSeq)

  }

}
