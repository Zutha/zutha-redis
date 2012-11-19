package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model._

trait ZFieldMember

trait IFieldMember extends ZFieldMember

case class IRoleFieldMember(role: IRef[IRole], players: Seq[IRef[IObject]])
  extends IFieldMember

case class ILiteralFieldMember(literalType: IRef[ILiteralType], values: Seq[LiteralValue])
  extends IFieldMember


trait MFieldMember extends ZFieldMember

case class MRoleFieldMember(role: MRef[MRole], players: Seq[MRef[MObject]])
  extends MFieldMember

case class MLiteralFieldMember(literalType: MRef[MLiteralType], values: Seq[LiteralValue])
  extends MFieldMember
