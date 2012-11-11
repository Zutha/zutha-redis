package net.zutha.redishost.model

trait ZFieldMember

case class ZFieldRoleMember(role: ZRef[ZRole], players: List[ZRef[ZObject]])
  extends ZFieldMember

case class ZFieldLiteralMember(literalType: ZRef[ZLiteralType], values: List[LiteralValue])
  extends ZFieldMember


trait IFieldMember extends ZFieldMember

case class IFieldRoleMember(role: IRef[IRole], players: List[IRef[IObject]])
  extends IFieldMember

case class IFieldLiteralMember(literalType: IRef[ILiteralType], values: List[LiteralValue])
  extends IFieldMember



trait MFieldMember extends ZFieldMember

case class MFieldRoleMember(role: MRef[MRole], players: List[MRef[MObject]])
  extends MFieldMember

case class MFieldLiteralMember(literalType: MRef[MLiteralType], values: List[LiteralValue])
  extends MFieldMember
