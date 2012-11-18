package net.zutha.redishost.db

import net.zutha.redishost.schema.{PSI, Name}

protected[db] trait RedisKeys {

  // Keys
  val idCounterKey = "idCounter"

  val INDEX_PREFIX = "index:"

  def indexKey( literalType: String, scope: String = "" ) = INDEX_PREFIX + literalType + ":" + scope
  def psiHashKey = indexKey( PSI.name )

  def nameHashKey = indexKey( Name.name )
  def nameHashKey( scope: String ) = indexKey( Name.name, scope )

  val OBJ_PREFIX = "obj:"

  def objHashKey( objId: String ): String = OBJ_PREFIX + objId
  def objZidsKey( objId: String ): String = OBJ_PREFIX + objId + ":zids"
  def objMergedZidsKey( objId: String ): String = OBJ_PREFIX + objId + ":mergedZids" //only used for merged objects
  def objDirectTypesKey( objId: String ): String = OBJ_PREFIX + objId + ":dTypes"
  def objAllTypesKey( objId: String ): String = OBJ_PREFIX + objId + ":types"
  def objFieldsKey( objId: String, fieldTypeZid: String ) = OBJ_PREFIX + objId + ":fields:" + fieldTypeZid

  val TYPE_PREFIX = "type:"

  def typeAllSupertypesKey( typeId: String ) = TYPE_PREFIX + typeId + ":supertypes"
  def typeDirectSupertypesKey( typeId: String ) = TYPE_PREFIX + typeId + ":dSupertypes"

  val OBJECT_TYPE_PREFIX = "objType:"

  def objTypeFieldDefs( objTypeId: String ) = OBJECT_TYPE_PREFIX + objTypeId + ":fieldDefs"

  val CLASS_PREFIX = "class:"

  def classInstancesKey( typeId: String ) = TYPE_PREFIX + typeId + ":instances"

  val FIELD_PREFIX = "field:"

  def fieldRolePlayersKey( fieldId: String, roleId: String ) = FIELD_PREFIX + fieldId + ":players:" + roleId
  def fieldLiteralsKey( fieldId: String, literalTypeId: String ) = FIELD_PREFIX + fieldId + ":literals:" + literalTypeId
  def fieldScopesKey( fieldId: String, scopeTypeId: String ) = FIELD_PREFIX + fieldId + ":scope:" + scopeTypeId

  // Hash Keys

  val objIsNewHKey = "isnew"
  val objZidHKey = "zid"
  val objLockHKey = "lock"
  val objClassHKey = "class"

}
