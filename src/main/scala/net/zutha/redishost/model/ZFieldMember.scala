package net.zutha.redishost.model

trait ZFieldMember

trait IFieldMember extends ZFieldMember

case class IRoleFieldMember(role: IRef[IRole], players: List[IRef[IObject]])
  extends IFieldMember

case class ILiteralFieldMember(literalType: IRef[ILiteralType], values: List[LiteralValue])
  extends IFieldMember


trait MFieldMember extends ZFieldMember

case class MRoleFieldMember(role: MRef[MRole], players: List[MRef[MObject]])
  extends MFieldMember

case class MLiteralFieldMember(literalType: MRef[MLiteralType], values: List[LiteralValue])
  extends MFieldMember
