package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldmember.{ILiteral, IRolePlayer}
import fieldset.IFieldSet
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import net.zutha.redishost.lib.ReflectionHelpers._
import scala.Some
import net.zutha.redishost.model.Zids
import net.zutha.redishost.model.IRef
import net.zutha.redishost.exception.SchemaException
import scala.reflect.runtime.universe._

trait ImmutableReadQueries extends ReadQueries { self: ImmutableAccessor =>

  // =================== Object Getters ======================

  def getObjectRef( objKey: String ): Option[IRef[IObject]] = objKey match {
    case Zid(zid) => getObjectZids( objKey ).toSeq match {
      case Seq() => None
      case zids => Some(IRef(Zids(zid, zids.toSeq.sorted)))
    }
    case _ => None
  }

  def getObjectByKey( key: String): Option[IObject] = {
    ???
  }

  def getItemByKey( key: String ): Option[IItem] = {
    ???
  }

  def getFieldByKey( key: String ): Option[IField] = {
    ???
  }

  protected[redishost] def getTypedObjectByKey[T <: IObject: TypeTag]( objKey: String ): Option[T] = {
    def wrongType: Nothing = {
      val tString = typeToString( typeOf[T] )
      throw new IllegalArgumentException( s"Object with id $objKey does not satisfy the type: $tString" )
    }

    getObjectRef( objKey ) flatMap { obj =>
    // If Object is an Item
      if ( objectHasType( obj, ZItem.refI ) ) {
        if ( typeOf[T] <:< typeOf[IItem] || typeOf[IItem] <:< typeOf[T] ){
          val item: Option[IItem] = getItemByKey( objKey )
          item map (_.asInstanceOf[T])
        }
        else wrongType
      }
      // If Object is a Field
      else if (objectHasType( obj, ZField.refI ) ) {
        if ( typeOf[T] <:< typeOf[IField] || typeOf[IField] <:< typeOf[T] ){
          val field: Option[IField] = getFieldByKey( objKey )
          field map (_.asInstanceOf[T])
        }
        else wrongType
      }
      // Object is neither an Item nor a Field
      else throw new SchemaException(
        s"Every object must be either an Item or a Field. Object with id: $objKey is neither." )
    }
  }

  def reloadObject[T <: IObject: TypeTag]( obj: IRef[T]): T = {
    getTypedObjectByKey( obj.key ).get
  }

  def reloadItem[T <: IItem: TypeTag]( item: IRef[T]): T = {
    getTypedObjectByKey( item.key).get
  }

  def reloadField[T <: IField: TypeTag]( field: IRef[T] ): T = {
    getTypedObjectByKey( field.key).get
  }


  // =================== Object Member Getters ======================

  // TODO: implement stub
  def getRolePlayersOfField(field: IRef[IField]): Set[IRolePlayer] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField(field: IRef[IField]): Set[ILiteral] = {
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

  def objectHasType( obj: IRef[IObject], zType: IRef[IObjectType] ): Boolean = {
    ???
  }
}
