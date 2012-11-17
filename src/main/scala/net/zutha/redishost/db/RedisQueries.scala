package net.zutha.redishost.db

protected[db] trait RedisQueries {

  val OBJ_PREFIX = "obj:"

  def objKey( objId: String ): String = OBJ_PREFIX + objId
  def objZidsKey( objId: String ): String = OBJ_PREFIX + objId + ":zids"
  def objDirectTypesKey( objId: String ): String = OBJ_PREFIX + objId + ":dTypes"
  def objAllTypesKey( objId: String ): String = OBJ_PREFIX + objId + ":types"
  def objFieldsKey( objId: String, fieldTypeZid: String ) = OBJ_PREFIX + objId + ":fields:" + fieldTypeZid

  val TYPE_PREFIX = "type:"

  def typeAllSupertypesKey( typeId: String ) = TYPE_PREFIX + typeId + ":supertypes"
  def typeDirectSupertypesKey( typeId: String ) = TYPE_PREFIX + typeId + ":dSupertypes"
  def typeInstancesKey( typeId: String ) = TYPE_PREFIX + typeId + ":instances"

  val FIELD_PREFIX = "field:"

  def fieldRolePlayersKey( fieldId: String, roleId: String ) = FIELD_PREFIX + fieldId + ":players:" + roleId
  def fieldLiteralsKey( fieldId: String, literalTypeId: String ) = FIELD_PREFIX + fieldId + ":literals:" + literalTypeId
  def fieldScopesKey( fieldId: String, scopeTypeId: String ) = FIELD_PREFIX + fieldId + ":scope:" + scopeTypeId

}
