package net.zutha.redishost

import db.{MutableAccessor, ImmutableAccessor}

package object model {

  def ??? : Nothing = throw new Error("Not Implemented")

  type RolePlayer                                   = (ZRef[ZRole], ZRef[ZObject])
  type RolePlayerSet                                = Set[RolePlayer]
  type RolePlayerMap                                = Map[ZRef[ZRole], Set[ZRef[ZObject]]]
  type IRolePlayer          = (IRef[IRole], IRef[IObject])
  type IRolePlayerSet       = Set[IRolePlayer]
  type IRolePlayerMap       = Map[IRef[IRole], Set[IRef[IObject]]]
  type MRolePlayer            = (MRef[MRole], MRef[MObject])
  type MRolePlayerSet         = Set[MRolePlayer]
  type MRolePlayerMap         = Map[MRef[MRole], Set[MRef[MObject]]]

  type LiteralR[R[T <: ZObject] <: ZRef[T]]         = (R[ZLiteralType], ZLiteralValue)
  type Literal                                      = (ZRef[ZLiteralType], ZLiteralValue)
  type LiteralSetR[R[T <: ZObject] <: ZRef[T]]                             = Set[LiteralR[R]]
  type LiteralSet                                   = Set[Literal]
  type LiteralMapR[R[T <: ZObject] <: ZRef[T]]      = Map[R[ZLiteralType], Set[ZLiteralValue]]
  type LiteralMap                                   = Map[ZRef[ZLiteralType], Set[ZLiteralValue]]
  type ILiteral             = (IRef[ILiteralType], ZLiteralValue)
  type ILiteralSet          = Set[ILiteral]
  type ILiteralMap          = Map[IRef[ILiteralType], Set[ZLiteralValue]]
  type MLiteral               = (MRef[MLiteralType], ZLiteralValue)
  type MLiteralSet            = Set[MLiteral]
  type MLiteralMap            = Map[MRef[MLiteralType], Set[ZLiteralValue]]

  type FieldList             = List[ZRef[ZField]]
  type IFieldList            = List[IRef[IField]]
  type MFieldList            = List[MRef[MField]]
}
