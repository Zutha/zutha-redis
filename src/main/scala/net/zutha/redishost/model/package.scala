package net.zutha.redishost

package object model {

  def ??? : Nothing = throw new Error("Not Implemented")

  type I = IObject
  type M = MObject

  type ScopeSeq                = Seq[(ZRef[ZScopeType], Seq[ZRef[ZObject]])]
  type ScopeMap                 = Map[ZRef[ZScopeType], Set[ZRef[ZObject]]]
  type IScopeSeq               = Seq[(IRef[IScopeType], Seq[IRef[IObject]])]
  type IScopeMap                = Map[IRef[IScopeType], Set[IRef[IObject]]]
  type MScopeSeq               = Seq[(MRef[MScopeType], Seq[MRef[MObject]])]
  type MScopeMap                = Map[MRef[MScopeType], Set[MRef[MObject]]]

  type RolePlayer           = (ZRef[ZRole], ZRef[ZObject])
  type RolePlayerSet        = Set[RolePlayer]
  type RolePlayerMap        = Map[ZRef[ZRole], Set[ZRef[ZObject]]]
  type IRolePlayer          = (IRef[IRole], IRef[IObject])
  type IRolePlayerSet       = Set[IRolePlayer]
  type IRolePlayerMap       = Map[IRef[IRole], Set[IRef[IObject]]]
  type MRolePlayer          = (MRef[MRole], MRef[MObject])
  type MRolePlayerSet       = Set[MRolePlayer]
  type MRolePlayerMap       = Map[MRef[MRole], Set[MRef[MObject]]]

  type Literal              = (ZRef[ZLiteralType], LiteralValue)
  type LiteralSet           = Set[Literal]
  type LiteralMap           = Map[ZRef[ZLiteralType], Set[LiteralValue]]
  type ILiteral             = (IRef[ILiteralType], LiteralValue)
  type ILiteralSet          = Set[ILiteral]
  type ILiteralMap          = Map[IRef[ILiteralType], Set[LiteralValue]]
  type MLiteral             = (MRef[MLiteralType], LiteralValue)
  type MLiteralSet          = Set[MLiteral]
  type MLiteralMap          = Map[MRef[MLiteralType], Set[LiteralValue]]

  type FieldSeq            = Seq[ZRef[ZField]]
  type IFieldSeq           = Seq[IRef[IField]]
  type MFieldSeq           = Seq[MRef[MField]]

}
