package net.zutha.redishost.db

import net.zutha.redishost.model._
import fieldset._
import itemclass._
import net.zutha.redishost.model.ScopeMatchType._
import scala.reflect.runtime.{currentMirror => mirror}

trait MutableReadQueries extends ReadQueries[MutableAccessor] { self: MutableAccessor =>

  // =================== Object Getters ======================

  protected[db] override def correctKey( key: String ): Option[String] = key match {
    case Zid(zid) => getObjectZids( key ).toSeq match {
      case Seq() => dbAcc.getObjectZids( key ) match {
        case Seq() => None
        case zids => Some( zids.head.key )
      }
      case zids => getObjectMergedZids( key ) match {
        case Seq() => Some( zids.head.key )
        case mZids => Some( mZids.head.key )
      }
    }
    case tempId => if ( objectIsNew( tempId ) )
      Some( tempId )
    else
      None
  }

  protected[db] override def objHasTypes( objKey: String, zTypeKeys: Iterable[String] ): Boolean = ???
  protected[db] override def objHasType( objKey: String, zTypeKey: String ): Boolean = ???


  // =================== Generic Queries ======================

  def objectIsNew( key: String ): Boolean = {
    redis.hexists( objHashKey( key ), objIsNewHKey )
  }

  override def objectHasType(obj: MRef[ZObject], zType: MRef[ZObjectType]) = ???

  // =================== Object Member Getters ======================

  override def getObjectZids( key: String ): Seq[Zid] = {
    val zids = super.getObjectZids( key ) union dbAcc.getObjectZids( key )
    zids.toSeq.sorted
  }

  def getObjectMergedZids( key: String ): Seq[Zid] = {
    redis.smembers[Zid]( objMergedZidsKey(key) ).get.map(_.get).toSeq.sorted
  }


  //  TODO: implement stub
  def getFieldSet( parent: MRef[ZObject],
                   role: MRef[ZRole],
                   fieldClass: MRef[ZFieldClass],
                   scopeFilter: MScopeSeq,
                   scopeMatchType: ScopeMatchType,
                   order: String,
                   limit: Int,
                   offset: Int
                   ): MFieldSet = {
    getFieldSet( parent, role, fieldClass, scopeFilter, scopeMatchType,
      order, limit, offset, includeDeleted_? = false )
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
                   ): MFieldSet = {
    val fields: MFieldSeq = ???
    val messages = Seq()
    MFieldSet( parent, role, fieldClass, fields, scopeFilter, scopeMatchType,
      order, limit, offset, messages, includeDeleted_? )
  }

}
