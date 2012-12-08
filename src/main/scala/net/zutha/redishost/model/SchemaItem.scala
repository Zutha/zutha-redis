package net.zutha.redishost.model

import companion.ZClassCompanion
import itemclass._
import literaltype.Name
import net.zutha.redishost.db.{MutableAccessor, ImmutableAccessor}
import net.zutha.redishost.exception.SchemaObjectMissingException

object SchemaItem {
  import scala.language.implicitConversions
  implicit def schemaItemToRefM[T <: MItem](si: SchemaItem{ type ObjTM = T })( implicit acc: MutableAccessor ): MRef[T] = si.refM( acc )
  implicit def schemaItemToRefI[T <: IItem](si: SchemaItem{ type ObjTI = T })( implicit acc: ImmutableAccessor ): IRef[T] = si.refI
}


protected[redishost] trait SchemaItem {
  /**
   * the ZClass of this Schema Item
   */
  type ObjC <: ZItem
  type ObjCI <: ObjC with IItem
  type ObjCM <: ObjC with MItem

  /**
   * the ZType(s) of this Schema Item. If it has multiple ZTypes, they should all be mixed in as Scala traits.
   */
  type ObjT <: ObjC
  type ObjTI <: ObjT with ObjCI
  type ObjTM <: ObjT with ObjCM

  /**
   * @return The Companion Object of the ZClass of the ZItem that this Scala Object is the Companion of
   */
  protected def classFactory: ZClassCompanion[ObjC, ObjCI, ObjCM]

  /**
   * @return the name of this Schema Item. This name is globally unique across the Zuthanet
   */
  def name: String

  /**
   * @return an IRef to the ZItem that this Scala Object is the companion of
   */
  def refI( implicit acc: ImmutableAccessor ): IRef[ObjTI] = {
    val ref = acc.lookupObjectIdByName( name ) match {
      case Some(key) => acc.getObjectRef(key).get
      case None => throw new SchemaObjectMissingException( name )
    }
    ref.asInstanceOf[IRef[ObjTI]]
  }

  /**
   * @return an MRef to the ZItem that this Scala Object is the companion of
   */
  def refM( implicit acc: MutableAccessor ): MRef[ObjTM] = {
    val ref = acc.lookupObjectIdByName(name) match {
      case Some(key) => acc.getObjectRef(key).get
      case None => MRef(TempId( Name.indexForm(name) ))
    }
    ref.asInstanceOf[MRef[ObjTM]]
  }
}
