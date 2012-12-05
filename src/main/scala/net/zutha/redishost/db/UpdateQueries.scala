package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldset._
import itemclass._
import literal.Name

trait UpdateQueries { self: MutableAccessor =>

  protected[db] def nextId: String = {
    val next = redis.incr( idCounterKey ).get
    "tmp:" + next.toString
  }

  /**
   * create a new object with a tempId
   * @return the tempId of the new object
   */
  protected[db] def createNewObject: String = {
    val newId = nextId
    redis.hset( OBJ_PREFIX + newId, objIsNewHKey, newId )
    newId
  }

  protected[redishost] def createSchemaRef( name: Name ): MRef[MObject] = {
    val newId = createNewObject
    redis.indexAddName( name.value, newId )
    MRef(TempId(newId))
  }

  def createItem( zClass: MRef[MItemClass] ): NewItem = {
    val newId = createNewObject
    redis.pipeline {r =>
      r.specifyObjectClass( newId, zClass.key )
    }
    NewItem( TempId(newId), zClass, Seq(), Seq() )
  }

  /**
   * Create a new field of the given class
   * @param zClass the class of field to create
   * @param rolePlayers a set of rolePlayers to put in the new field
   * @param literals a set of literal values to put in the new field
   * @param scope the scope in which the new field will be applicable
   * @return a subclass of NewField, depending on the field class requested by zClass
   */
  def createField( zClass: MRef[MFieldClass],
                   rolePlayers: MRolePlayerSet,
                   literals: MLiteralSet,
                   scope: MScopeMap
                   ): NewField = {
    val newId = createNewObject

    redis.pipeline {r =>
      r.specifyObjectClass( newId, zClass.key )
      r.addTypeToObject( newId, zClass.key )
      r.addRolePlayersToField( newId, rolePlayers )
      r.setFieldLiterals( newId, literals )
      r.setFieldScope( newId, scope )
    }

    val literalMembers = literals.map{ case (lType, value) =>
      MLiteralFieldMember(lType, value)
    }.toSeq
    val roleMembers = rolePlayers.mapValues(_.toSeq).map{case (role, players) =>
      MRoleFieldMember(role, players)
    }.toSeq
    val members: Seq[MFieldMember] = roleMembers ++ literalMembers
    val scopeSeq = scope.mapValues(_.toSeq).toSeq

    //TODO create simpler FieldType if applicable
    NewComplexField( TempId(newId), zClass, Seq(), members, scopeSeq)
  }


}
