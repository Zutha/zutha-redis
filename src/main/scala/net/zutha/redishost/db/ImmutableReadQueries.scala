package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldmember.{ILiteral, IRolePlayer}
import fieldset.IFieldSet
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import scala.Some
import net.zutha.redishost.model.IRef

trait ImmutableReadQueries extends ReadQueries[IObject] { self: ImmutableAccessor =>

  // =================== Object Getters ======================

  def getObjectRef( key: String ): Option[IRef[ZObject]] = key match {
    case Zid(zid) => getObjectZids( key ).toSeq match {
      case Seq() => None
      case zids => Some(Ref( zids.head.key ))
    }
    case _ => None
  }

  def getObjectByKey( key: String): Option[I[ZObject]] = {
    ???
  }

  def getItemByKey( key: String ): Option[II[ZItem]] = {
    ???
  }

  def getFieldByKey( key: String ): Option[IF[ZField]] = {
    ???
  }


  // =================== Generic Queries ======================
  def objectHasType( obj: Ref[IObject, ZObject],
                     zType: Ref[IObject, ZObjectType] ): Boolean  = {
    ???
  }


  // =================== Object Member Getters ======================

  // TODO: implement stub
  def getRolePlayersOfField(field: IRef[ZField]): Set[IRolePlayer] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: IRef[ZField]): Set[ILiteral] = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: IRef[ZObject],
                   role: IRef[ZRole],
                   fieldClass: IRef[ZFieldClass],
                   scopeFilter: IScopeSeq,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int
                   ): IFieldSet = {
    val fields: IFieldSeq = ???
    IFieldSet( parent, role, fieldClass, fields, scopeFilter, scopeMatchType, order, limit, offset )
  }

}
