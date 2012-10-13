package net.zutha.redishost.db

import net.zutha.redishost.model._
import common._

trait ReadQueries {

  // TODO: implement stub
  def getRolePlayersOfField(field: ZField): Set[(ZRole, ZItem)] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: ZField): Set[ZLiteral] = {
    ???
  }

  //  TODO: implement stub
  def getFieldSetFields( parent: ZObject,
                         role: ZRole,
                         fieldClass: ZFieldClass,
                         limit: Int,
                         offset: Int
                        ): Map[ZFieldIdentity, ZPersistedField] = {
    ???
  }

  // TODO: implement stub
  def getUpdatedObject( zObject: ZObject, fieldLimit: Int = 0): ZPersistedObject = ???

  // TODO: implement stub
  def getUpdatedField( field: ZField, fieldLimit: Int = 0 ): ZPersistedField = ???

  // TODO: implement with single redis request
  def getUpdatedFields( fields: ZField* ): Map[ZFieldIdentity, ZPersistedField] = {
    fields.map{ f =>
      val newf = getUpdatedField(f)
      (newf.id -> newf)
    }.toMap
  }

}
