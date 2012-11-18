package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaObjectMissingException

private[redishost] trait SchemaObject {
  type ObjT <: ZObject
  type ObjTI <: IObject
  type ObjTM <: MObject

  def name: String

  def refI( implicit acc: ImmutableAccessor ): IRef[ObjTI] = {
    val ref = acc.lookupObjectIdByName( name ) match {
      case Some(key) => acc.getObjectRef(key).get
      case None => throw new SchemaObjectMissingException( name )
    }
    ref.asInstanceOf[IRef[ObjTI]]
  }

  def refM( implicit acc: MutableAccessor ): MRef[ObjTM] = {
    val ref = acc.lookupObjectIdByName(name) match {
      case Some(key) => acc.getObjectRef(key).get
      case None => acc.createSchemaRef( name )
    }
    ref.asInstanceOf[MRef[ObjTM]]
  }
}
