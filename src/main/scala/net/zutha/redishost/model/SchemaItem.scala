package net.zutha.redishost.model

import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaObjectMissingException

object SchemaItem {
  implicit def siToRefM[T <: MItem](si: SchemaItem{ type ObjTM = T })( implicit acc: MutableAccessor ): MRef[T] = si.refM( acc )
  implicit def siToRefI[T <: IItem](si: SchemaItem{ type ObjTI = T })( implicit acc: ImmutableAccessor ): IRef[T] = si.refI
}

protected[redishost] trait SchemaItem {
  type ObjT <: ZItem
  type ObjTI <: ObjT with IItem
  type ObjTM <: ObjT with MItem

  def classFactory: ObjectFactory[ObjT, ObjTI, ObjTM]

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
