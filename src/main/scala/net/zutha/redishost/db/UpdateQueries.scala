package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldmember.{MLiteral, MRolePlayer, MRoleFieldMember, MFieldMember}
import itemclass._
import net.zutha.redishost.exception.SchemaException

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
                   rolePlayers: Set[MRolePlayer],
                   literals: Set[MLiteral],
                   scope: MScopeMap
                   ): NewField = {
    val newId = createNewObject

    //TODO validate provided field components against fieldClass declarations

    redis.pipeline {r =>
      r.specifyObjectClass( newId, zClass.key )
      r.addTypeToObject( newId, zClass.key )
      r.addRolePlayersToField( newId, rolePlayers )
      r.setFieldLiterals( newId, literals )
      r.setFieldScope( newId, scope )
    }

    val roleMembers = rolePlayers.groupBy(_.role).map{ case (role, rps) =>
      MRoleFieldMember(role, rps.map(_.player).toSeq )
    }.toSeq
    val literalMembers = literals.toSeq
    val members: Seq[MFieldMember] = roleMembers ++ literalMembers
    val scopeSeq = scope.mapValues(_.toSeq).toSeq

    //TODO create simpler FieldType if applicable
    NewComplexField( TempId(newId), zClass, Seq(), members, scopeSeq)
  }

  /**
   * modify the rolePlayers of a NewBinaryField or
   * replace a ModifiedBinaryField with a NewBinaryField of the same FieldClass but different rolePlayers
   * @param field the NewBinaryField to be updated or the ModifiedBinaryField to be updated/replaced
   * @param rolePlayer1 the new value of one of the two RolePlayers of this Binary Field
   * @param rolePlayer2 the new value of the other RolePlayer of this Binary Field
   * @return the same NewBinaryField updated with the given rolePlayers
   *         or a NewBinaryField that has the same FieldClass as `field`
   *         but with the given rolePlayers
   */
  def updateField( field: MBinaryField,
                   rolePlayer1: MRolePlayer,
                   rolePlayer2: MRolePlayer
                   ): NewBinaryField = field match {
    case NewBinaryField( id, zClass, fieldSets, rp1, rp2, scope,
                         msgs, memberMsgs, scopeMsgs, deleted_? ) => {
      // TODO update redis
      NewBinaryField( id, zClass, fieldSets, rolePlayer1, rolePlayer2, scope,
        msgs, memberMsgs, scopeMsgs, deleted_? )
    }
    case ModifiedBinaryField( modifiedFieldId, zClass, fieldSets, rp1, rp2, scope,
                              msgs, memberMsgs, scopeMsgs, deleted_? ) => {
      // TODO delete modifiedField
      createField( zClass, Set( rolePlayer1, rolePlayer2 ), Set(), scope ) match {
        case f: NewBinaryField => f
        case f => throw new SchemaException(
          "A binary field should have been created. Actually returned: " + f.toString )
      }
    }
  }

}
