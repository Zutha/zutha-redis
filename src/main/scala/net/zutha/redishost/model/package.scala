package net.zutha.redishost

import db.{MutableAccessor, ImmutableAccessor}

package object model {

  def ??? : Nothing = throw new Error("Not Implemented")

  type Scope                = List[(ZRef[ZScopeType], List[ZRef[ZObject]])]
  type IScope               = List[(IRef[IScopeType], List[IRef[IObject]])]
  type MScope               = List[(MRef[MScopeType], List[MRef[MObject]])]

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

  type FieldList            = List[ZRef[ZField]]
  type IFieldList           = List[IRef[IField]]
  type MFieldList           = List[MRef[MField]]
}
