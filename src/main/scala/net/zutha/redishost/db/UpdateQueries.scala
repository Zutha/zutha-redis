package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldmember._
import itemclass._
import net.zutha.redishost.exception.SchemaException

trait UpdateQueries { self: MutableAccessor =>

  protected[db] def nextId: String = {
    val next = redis.incr( idCounterKey ).get
    "tmp:" + next.toString
  }

  // ======================= Object Creation =======================

  /**
   * create a new object with a tempId
   * @return the tempId of the new object
   */
  protected[db] def createNewObject: String = {
    val newId = nextId
    redis.hset( OBJ_PREFIX + newId, objIsNewHKey, newId )
    newId
  }

  def createItem( zClass: MRef[ZItemClass] ): NewItem = {
    val newId = createNewObject
    redis.pipeline {r =>
      r.specifyObjectClass( newId, zClass.key )
    }
    NewItem( newId , zClass, Seq(), Seq() )
  }

  /**
   * Create a new field of the given class
   * @param fieldClass the class of field to create
   * @param rolePlayers a set of rolePlayers to put in the new field
   * @param literals a set of literal values to put in the new field
   * @param scope the scope in which the new field will be applicable
   * @return a subclass of NewField, depending on the field class requested by fieldClass
   */
  def createField( fieldClass: MRef[ZFieldClass],
                   rolePlayers: Set[MRolePlayer],
                   literals: MLiteralMap,
                   scope: MScopeMap
                   ): NewField = {
    val newId = createNewObject

    //TODO validate provided field components against fieldClass declarations

    redis.pipeline {r =>
      r.specifyObjectClass( newId, fieldClass.key )
      r.addTypeToObject( newId, fieldClass.key )
      r.addRolePlayersToField( newId, rolePlayers )
      r.setFieldLiterals( newId, literals )
      r.setFieldScope( newId, scope )
    }

    val roleMembers = rolePlayers.groupBy(_.role).map{ case (role, rps) =>
      RoleFieldMember(role, rps.map(_.player).toSeq )
    }.toSeq
    val literalMembers: Seq[MLiteralFieldMember] = literals.map( pair => (pair: MLiteralFieldMember) ).toSeq
    val members: Seq[MFieldMember] = roleMembers ++ literalMembers
    val scopeSeq = scope.mapValues(_.toSeq).toSeq

    //TODO create simpler FieldType if applicable
    NewComplexField( newId, fieldClass, Seq(), members, scopeSeq)
  }

  // ======================= Mutation =======================

  def deleteObject( obj: MRef[ZObject] ) {
    ???
  }

  def restoreObject( obj: MRef[ZObject] ) {
    ???
  }

  def changeItemClass( item: MRef[ZItem], newClass: MRef[ZItemClass] ) {
    ???
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
  def updateField( field: MutableBinaryField,
                   rolePlayer1: MRolePlayer,
                   rolePlayer2: MRolePlayer
                   ): NewBinaryField = field match {
    case NewBinaryField( key, zClass, fieldSets, rp1, rp2, scope,
                         msgs, memberMsgs, scopeMsgs, deleted_? ) => {
      // TODO update redis
      NewBinaryField( key, zClass, fieldSets, rolePlayer1, rolePlayer2, scope,
        msgs, memberMsgs, scopeMsgs, deleted_? )
    }
    case ModifiedBinaryField( primaryZids, allZids, zClass, fieldSets, rp1, rp2, scope,
                              msgs, memberMsgs, scopeMsgs, deleted_? ) => {
      // TODO delete modifiedField
      createField( zClass, Set( rolePlayer1, rolePlayer2 ), Map(), scope ) match {
        case f: NewBinaryField => f
        case f => throw new SchemaException(
          "A binary field should have been created. Actually returned: " + f.toString )
      }
    }
  }

  def updateField( field: MRef[ZField],
                   newRolePlayers: Set[MRolePlayer],
                   newLiterals: MLiteralMap
                   ) {
    ???
  }

}
