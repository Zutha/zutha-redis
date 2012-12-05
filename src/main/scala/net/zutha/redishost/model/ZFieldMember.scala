package net.zutha.redishost.model

import itemclass._
import literal.LiteralValue

object ZFieldMember {
  import scala.language.implicitConversions
  implicit def rolePlayerToFieldMember( rolePlayer: IRolePlayer ): IFieldMember = rolePlayer match {
    case (role, player) => IRoleFieldMember( role, Seq(player) )
  }
  implicit def rolePlayerToFieldMember( rolePlayer: MRolePlayer ): MFieldMember = rolePlayer match {
    case (role, player) => MRoleFieldMember( role, Seq(player) )
  }
  implicit def literalToFieldMember( literal: ILiteral ): IFieldMember = literal match {
    case (literalType, literalValue) => ILiteralFieldMember( literalType, literalValue )
  }
  implicit def literalToFieldMember( literal: MLiteral ): MFieldMember = literal match {
    case (literalType, literalValue) => MLiteralFieldMember( literalType, literalValue )
  }
}

trait ZFieldMember

trait IFieldMember extends ZFieldMember

case class IRoleFieldMember(role: IRef[IRole], players: Seq[IRef[IObject]])
  extends IFieldMember

case class ILiteralFieldMember(literalType: IRef[ILiteralType], value: LiteralValue)
  extends IFieldMember


trait MFieldMember extends ZFieldMember

case class MRoleFieldMember(role: MRef[MRole], players: Seq[MRef[MObject]])
  extends MFieldMember

case class MLiteralFieldMember(literalType: MRef[MLiteralType], value: LiteralValue)
  extends MFieldMember
