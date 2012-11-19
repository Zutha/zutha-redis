package net.zutha.redishost.model

import companion.ZItemClassCompanion
import itemclass._
import literal.Name
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaObjectMissingException

object SchemaItem {
  import scala.language.implicitConversions
  implicit def siToRefM[T <: MItem](si: SchemaItem{ type ObjTM = T })( implicit acc: MutableAccessor ): MRef[T] = si.refM( acc )
  implicit def siToRefI[T <: IItem](si: SchemaItem{ type ObjTI = T })( implicit acc: ImmutableAccessor ): IRef[T] = si.refI
}

protected[redishost] trait SchemaItem {
  type ObjC <: ZItem
  type ObjCI <: ObjC with IItem
  type ObjCM <: ObjC with MItem

  type ObjT <: ObjC
  type ObjTI <: ObjT with ObjCI
  type ObjTM <: ObjT with ObjCM

  def classFactory: ZItemClassCompanion[ObjC, ObjCI, ObjCM]

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
      case None => MRef(TempId( Name(name).indexForm ))
    }
    ref.asInstanceOf[MRef[ObjTM]]
  }
}
