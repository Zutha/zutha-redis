package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldset._
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._

trait ImmutableReadQueries extends ReadQueries { self: ImmutableAccessor =>

  // =================== Getters ======================

  def getObjectRef( objKey: String ): Option[IRef[IObject]] = objKey match {
    case Zid(zid) => getObjectZids( objKey ).toSeq match {
      case Seq() => None
      case zids => Some(IRef(Zids(zid, zids.toSeq.sorted)))
    }
    case _ => None
  }

  def getObjectRaw( objKey: String, fieldLimit: Int = 0 ): Option[ZObject] = ???

  def getObject(id: Zid, fieldLimit: Int = 0): Option[IObject] = {
    ???
  }

  def getObjectT[T <: IObject](id: Zid, fieldLimit: Int = 0): Option[T] = {
    getObject(id, fieldLimit) map (_.asInstanceOf[T])
  }

  def getItem( item: IRef[IItem], fieldLimit: Int = 0): IItem = {
    getObject(item.zid, fieldLimit).get.asInstanceOf[IItem]
  }

  def getField( field: IRef[IField], fieldLimit: Int = 0 ): IField = {
    getObject(field.zid, fieldLimit).get.asInstanceOf[IField]
  }

  // TODO: implement stub
  def getRolePlayersOfField(field: IRef[IField]): IRolePlayerSet = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: IRef[IField]): ILiteralSet = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: IRef[IObject],
                   role: IRef[IRole],
                   fieldClass: IRef[IFieldClass],
                   scopeFilter: IScopeSeq,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int
                   ): IFieldSet = {
    val fields: IFieldSeq = ???
    IFieldSet( parent, role, fieldClass, fields, scopeFilter, scopeMatchType, order, limit, offset )
  }

  // =================== Queries ======================

  def objHasType( zType: IRef[IType] ): Boolean = {
    ???
  }

}
