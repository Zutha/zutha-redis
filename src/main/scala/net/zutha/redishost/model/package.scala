package net.zutha.redishost

import db.{MutableAccessor, ImmutableAccessor}

package object model {

  def ??? : Nothing = throw new Error("Not Implemented")



  type RefT[T <: ZObject]                                         = ZRef with T
  type IRefT[A <: ImmutableAccessor, +T[A1 <: A] <: IObject[A1]]  = IRef[T[A]] with T[A]
  type IRefTA[A <: ImmutableAccessor, +TA <: IObject[A]]          = IRef[TA] with TA
  type MRefT[A <: MutableAccessor, +T[A1 <: A] <: MObject[A1]]    = MRef[T[A]] with T[A]
  type MRefTA[A <: MutableAccessor, +TA <: MObject[A]]            = MRef[TA] with TA

  type ObjectT[T]                                                       = ZObject with T
  type ZObjectT[T]                                                      = ZObject with T
  type IObjectT[A <: ImmutableAccessor, +T[A1 <: A] <: IObject[A1]]     = IObject[A] with T[A]
  type MObjectT[A <: MutableAccessor, +T[A1 <: A] <: MObject[A1]]       = MObject[A] with T[A]


  type RolePlayer                             = (RefT[ZRole], RefT[ZObject])
  type RolePlayerSet                          = Set[RolePlayer]
  type RolePlayerMap                          = Map[RefT[ZRole], Set[RefT[ZObject]]]
  type IRolePlayer[A <: ImmutableAccessor]    = (IRefT[A, IRole], IRefT[A, IObject])
  type IRolePlayerSet[A <: ImmutableAccessor] = Set[IRolePlayer[A]]
  type IRolePlayerMap[A <: ImmutableAccessor] = Map[IRefT[A, IRole], Set[IRefT[A, IObject]]]
  type MRolePlayer[A <: MutableAccessor]      = (MRefT[A, MRole], MRefT[A, MObject])
  type MRolePlayerSet[A <: MutableAccessor]   = Set[MRolePlayer[A]]
  type MRolePlayerMap[A <: MutableAccessor]   = Map[MRefT[A, MRole], Set[MRefT[A, MObject]]]

  type Literal                                = (RefT[ZLiteralType], ZLiteralValue)
  type LiteralSet                             = Set[Literal]
  type LiteralMap                             = Map[RefT[ZLiteralType], Set[ZLiteralValue]]
  type ILiteral[A <: ImmutableAccessor]       = (IRefT[A, ILiteralType], ZLiteralValue)
  type ILiteralSet[A <: ImmutableAccessor]    = Set[ILiteral[A]]
  type ILiteralMap[A <: ImmutableAccessor]    = Map[IRefT[A, ILiteralType], Set[ZLiteralValue]]
  type MLiteral[A <: MutableAccessor]         = (MRefT[A, MLiteralType], ZLiteralValue)
  type MLiteralSet[A <: MutableAccessor]      = Set[MLiteral[A]]
  type MLiteralMap[A <: MutableAccessor]      = Map[MRefT[A, MLiteralType], Set[ZLiteralValue]]

  type FieldMap                               = Map[ZFieldIdentity, ZField]
  type FieldSetMap                            = Map[(RefT[ZRole], RefT[ZFieldClass]), ZFieldSet]
  type IFieldMap[A <: ImmutableAccessor]      = Map[Zid, IField[A]]
  type IFieldSetMap[A <: ImmutableAccessor]   = Map[(IRefT[A, IRole], IRefT[A, IFieldClass]), IFieldSet[A]]
  type MFieldMap[A <: MutableAccessor]        = Map[ZFieldIdentity, MField[A]]
  type MFieldSetMap[A <: MutableAccessor]     = Map[(MRefT[A, MRole], MRefT[A, MFieldClass]), MFieldSet[A]]
}
