package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldclass._
import fieldmember._
import fieldset._
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import scala.reflect.runtime.{currentMirror => mirror}

trait MutableReadQueries extends ReadQueries[MObject] { self: MutableAccessor =>

  // =================== Object Getters ======================

  def getObjectRef( key: String ): Option[MRef[ZObject]] = key match {
    case Zid(zid) => getObjectZids( key ).toSeq match {
      case Seq() => dbAcc.getObjectZids( key ) match {
        case Seq() => None
        case zids => Some(Ref( zids.head.key ))
      }
      case zids => getObjectMergedZids( key ) match {
        case Seq() => Some(Ref( zids.head.key ))
        case mZids => Some(Ref( mZids.head.key ))
      }
    }
    case tempId => if ( objectIsNew( tempId ) )
      Some(Ref( tempId  ))
    else
      None
  }

  def getObjectByKey( key: String): Option[M[ZObject]] = {
    // check if this object is an item or field and then delegate to getItem../getField..
    ???
  }

  def getItemByKey( key: String ): Option[MI[ZItem]] = {
    ???
  }

  def getFieldByKey( key: String ): Option[MF[ZField]] = {
    ???
  }


  // =================== Generic Queries ======================

  def objectIsNew( key: String ): Boolean = {
    redis.hexists( objHashKey( key ), objIsNewHKey )
  }

  val x1: MRef[ZObject] = ???
  val x2: Ref[MObject, M[ZObject]] = x1
  val x3: Ref[MObject, M[ZObject]] = x2

  def objectHasType( obj: Ref[MObject, ZObject],
                     zType: Ref[MObject, ZObjectType] ): Boolean  = {
    ???
  }

  // =================== Object Member Getters ======================

  override def getObjectZids( key: String ): Seq[Zid] = {
    val zids = super.getObjectZids( key ) union dbAcc.getObjectZids( key )
    zids.toSeq.sorted
  }

  def getObjectMergedZids( key: String ): Seq[Zid] = {
    redis.smembers[Zid]( objMergedZidsKey(key) ).get.map(_.get).toSeq.sorted
  }

  // TODO: implement stub
  def getRolePlayersOfField( field: MRef[ZField] ): Set[MRolePlayer] = {
    ???
  }

  //  TODO: implement stub
  def getLiteralsOfField( field: MRef[ZField] ): MLiteralMap = {
    ???
  }

  //  TODO: implement stub
  def getFieldSet( parent: MRef[ZObject],
                   role: MRef[ZRole],
                   fieldClass: MRef[ZFieldClass],
                   scopeFilter: MScopeSeq,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int,
                   includeDeleted_? : Boolean
                   ): ZFieldSet = {
    val fields: MFieldSeq = ???
    val messages = Seq()
    MFieldSet( parent, role, fieldClass, fields, scopeFilter, scopeMatchType,
      order, limit, offset, includeDeleted_?, messages )
  }

  // TODO: implement stub
  def getUpdatedItem( item: MRef[ModifiedItem] ): ModifiedItem = ???

  // TODO: implement stub
  def getUpdatedField( field: MRef[ModifiedField] ): ModifiedField = ???

}
