package net.zutha.redishost

import db.{MutableAccessor, ImmutableAccessor}

package object model {

  def ??? : Nothing = throw new Error("Not Implemented")


  type ObjectT[T]                                        = ZObject with T

  type ReferenceT[T <: ZObject]                                    = ZObjectReference[T] with T
  type IReferenceT[A <: ImmutableAccessor, +T[A1 <: A] <: ZImmutableObject[A1]]  = ZImmutableObjectReference[A, T[A]] with T[A]
  type IReferenceTA[A <: ImmutableAccessor, +TA <: ZImmutableObject[A]]         = ZImmutableObjectReference[A, TA] with TA
  type MReferenceT[A <: MutableAccessor, +T[A1 <: A] <: ZMutableObject[A1]]    = ZMutableObjectReference[A, T[A]] with T[A]
  type MReferenceTA[A <: MutableAccessor, +TA <: ZMutableObject[A]]           = ZMutableObjectReference[A, TA] with TA

  type CObjectT[T]                                       = ZConcreteObject with T
  type CImmutableT[A <: ImmutableAccessor, +T[A1 <: A] <: ZImmutableObject[A1]] = ZConcreteImmutableObject[A] with T[A]
  type CMutableT[A <: MutableAccessor, +T[A1 <: A] <: ZMutableObject[A1]]      = ZConcreteMutableObject[A] with T[A]


  type RolePlayer                             = (ReferenceT[ZRole], ReferenceT[ZObject])
  type RolePlayerSet = Set[RolePlayer]
  type RolePlayerMap                          = Map[ReferenceT[ZRole], Set[ReferenceT[ZObject]]]
  type IRolePlayer[A <: ImmutableAccessor]    = (IReferenceT[A, ZIRole], IReferenceT[A, ZImmutableObject])
  type IRolePlayerSet[A <: ImmutableAccessor] = Set[IRolePlayer[A]]
  type IRolePlayerMap[A <: ImmutableAccessor] = Map[IReferenceT[A, ZIRole], Set[IReferenceT[A, ZImmutableObject]]]
  type MRolePlayer[A <: MutableAccessor]      = (MReferenceT[A, ZMRole], MReferenceT[A, ZMutableObject])
  type MRolePlayerSet[A <: MutableAccessor]   = Set[MRolePlayer[A]]
  type MRolePlayerMap[A <: MutableAccessor]   = Map[MReferenceT[A, ZMRole], Set[MReferenceT[A, ZMutableObject]]]

  type Literal                                = (ReferenceT[ZLiteralType], ZLiteralValue)
  type LiteralSet                             = Set[Literal]
  type LiteralMap                             = Map[ReferenceT[ZLiteralType], Set[ZLiteralValue]]
  type ILiteral[A <: ImmutableAccessor]       = (IReferenceT[A, ZILiteralType], ZLiteralValue)
  type ILiteralSet[A <: ImmutableAccessor]    = Set[ILiteral[A]]
  type ILiteralMap[A <: ImmutableAccessor]    = Map[IReferenceT[A, ZILiteralType], Set[ZLiteralValue]]
  type MLiteral[A <: MutableAccessor]         = (MReferenceT[A, ZMLiteralType], ZLiteralValue)
  type MLiteralSet[A <: MutableAccessor]      = Set[MLiteral[A]]
  type MLiteralMap[A <: MutableAccessor]      = Map[MReferenceT[A, ZMLiteralType], Set[ZLiteralValue]]

  type FieldMap                               = Map[ZFieldIdentity, ZField]
  type FieldSetMap                            = Map[(ReferenceT[ZRole], ReferenceT[ZFieldClass]), ZFieldSet]
  type IFieldMap[A <: ImmutableAccessor]      = Map[Zid, ZImmutableField[A]]
  type IFieldSetMap[A <: ImmutableAccessor]   = Map[(IReferenceT[A, ZIRole], IReferenceT[A, ZIFieldClass]), ZImmutableFieldSet[A]]
  type MFieldMap[A <: MutableAccessor]        = Map[ZFieldIdentity, ZMutableField[A]]
  type MFieldSetMap[A <: MutableAccessor]     = Map[(MReferenceT[A, ZMRole], MReferenceT[A, ZMFieldClass]), ZMutableFieldSet[A]]
}
