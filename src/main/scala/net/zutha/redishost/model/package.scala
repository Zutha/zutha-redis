package net.zutha.redishost

import db.{MutableAccessor, ImmutableAccessor}

package object model {

  def ??? : Nothing = throw new Error("Not Implemented")



  type RefT[T <: ZObject]                                         = ZRef with T
  type IRefT[+T <: IObject]  = IRefTA[T]
  type IRefTA[+TA <: IObject]          = IRef with TA
  type MRefT[+T <: MObject]    = MRefTA[T]
  type MRefTA[+TA <: MObject]            = MRef with TA

  type ObjectT[T]                                                       = ZObject with T
  type ZObjectT[T]                                                      = ZObject with T
  type IObjectT[+T <: IObject]     = IObject with T
  type MObjectT[+T <: MObject]       = MObject with T


  type RolePlayer                                   = (RefT[ZRole], RefT[ZObject])
  type RolePlayerSet                                = Set[RolePlayer]
  type RolePlayerMap                                = Map[RefT[ZRole], Set[RefT[ZObject]]]
  type IRolePlayer          = (IRefT[IRole], IRefT[IObject])
  type IRolePlayerSet       = Set[IRolePlayer]
  type IRolePlayerMap       = Map[IRefT[IRole], Set[IRefT[IObject]]]
  type MRolePlayer            = (MRefT[MRole], MRefT[MObject])
  type MRolePlayerSet         = Set[MRolePlayer]
  type MRolePlayerMap         = Map[MRefT[MRole], Set[MRefT[MObject]]]

  type LiteralR[R[T <: ZObject] <: RefT[T]]         = (R[ZLiteralType], ZLiteralValue)
  type Literal                                      = (RefT[ZLiteralType], ZLiteralValue)
  type LiteralSetR[R[T <: ZObject] <: RefT[T]]                             = Set[LiteralR[R]]
  type LiteralSet                                   = Set[Literal]
  type LiteralMapR[R[T <: ZObject] <: RefT[T]]      = Map[R[ZLiteralType], Set[ZLiteralValue]]
  type LiteralMap                                   = Map[RefT[ZLiteralType], Set[ZLiteralValue]]
  type ILiteral             = (IRefT[ILiteralType], ZLiteralValue)
  type ILiteralSet          = Set[ILiteral]
  type ILiteralMap          = Map[IRefT[ILiteralType], Set[ZLiteralValue]]
  type MLiteral               = (MRefT[MLiteralType], ZLiteralValue)
  type MLiteralSet            = Set[MLiteral]
  type MLiteralMap            = Map[MRefT[MLiteralType], Set[ZLiteralValue]]

  type FieldMap                                     = Map[ZFieldIdentity, ZField]
  type FieldSetMap                                  = Map[(RefT[ZRole], RefT[ZFieldClass]), ZFieldSet]
  type IFieldMap            = Map[Zid, IField]
  type IFieldSetMap         = Map[(IRefT[IRole], IRefT[IFieldClass]), IFieldSet]
  type MFieldMap              = Map[ZFieldIdentity, MField]
  type MFieldSetMap           = Map[(MRefT[MRole], MRefT[MFieldClass]), MFieldSet]
}
