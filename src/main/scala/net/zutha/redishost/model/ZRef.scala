package net.zutha.redishost.model

import fieldclass.{IField, MField, ZField}
import net.zutha.redishost.db.Accessor
import itemclass._
import scala.reflect.runtime.universe._
import net.zutha.redishost.exception.{ZTypeChangedException, ZTypeMismatchException}
import net.zutha.redishost.model.special.ZNothing

object ZRef {
  import scala.language.implicitConversions
  implicit def refToIObject( ref: ZRef[IA, ZObject] ): IObject = ref.load[IObject]
  implicit def refToMObject( ref: ZRef[MA, ZObject] ): MObject = ref.load[MObject]

  implicit def refToIItem[ZT >: ZNothing <: ZItem]( ref: ZRef[IA, ZT] ): Obj[IItem, ZT] = ref.load[IItem]
  implicit def refToMItem[ZT >: ZNothing <: ZItem]( ref: ZRef[MA, ZT] ): Obj[MItem, ZT] = ref.load[MItem]

  implicit def refToIField[ZT >: ZNothing <: ZField]( ref: ZRef[IA, ZT] ): Obj[IField, ZT] = ref.load[IField]
  implicit def refToMField[ZT >: ZNothing <: ZField]( ref: ZRef[MA, ZT] ): Obj[MField, ZT] = ref.load[MField]
}

case class ZRef
[A <: Accessor[A], +ZT >: ZNothing <: ZObject] private[redishost]( zKey: String )
                                                                 ( implicit val acc: A,
                                                                   private[this] val tt: TypeTag[ZT] )
{
  private def load[Impl <: ZObject : TypeTag]: Obj[Impl, ZT] = try {
    acc.loadObject[Impl, ZT]( this )
  } catch {
    // TODO throw informative exception ( object's type changed since ref creation )
    case e: ZTypeMismatchException => throw new ZTypeChangedException
  }

}
