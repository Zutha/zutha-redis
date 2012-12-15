package net.zutha.redishost

import db.{Accessor, MutableAccessor, ImmutableAccessor}
import model.special.ZNothing


package object model {
  import itemclass._
  import fieldclass._
  import scala.language.implicitConversions

  // Accessor Aliases
  type IA = ImmutableAccessor
  type MA = MutableAccessor

  // --- Type Constructors for Mutability ---

  // Object Type Constructors
  type Obj[+Impl <: ZObject, +ZT >: ZNothing <: ZObject] = Impl with ZT with Loadable[Impl, ZT]

//  type IObj[+T >: ZNothing <: ZObject]  = IObject with T
//  type MObj[+T >: ZNothing <: ZObject]  = MObject with T
//
//  // Item Type Constructors
//  type II[+T >: ZNothing <: ZItem]   = IItem with T
//  type MI[+T >: ZNothing <: ZItem]   = MItem with T
//
//  // Field Type Constructors
//  type IF[+T >: ZNothing <: ZField]  = IField with T
//  type MF[+T >: ZNothing <: ZField]  = MField with T

  // ZRef Aliases
  type IRef[+T >: ZNothing <: ZObject] = ZRef[IA, T]
  type MRef[+T >: ZNothing <: ZObject] = ZRef[MA, T]

  // Scope structures
  implicit def ScopeSeqToMap[A <: Accessor[A]]( scope: ScopeSeq[A] ): ScopeMap[A] = scope.toMap.mapValues(_.toSet)
  type ScopeSeq[A <: Accessor[A]]  = Seq[(ZRef[A, ZScopeType], Seq[ZRef[A, ZObject]])]
  type IScopeSeq    = ScopeSeq[IA]
  type MScopeSeq    = ScopeSeq[MA]

  type ScopeMap[A <: Accessor[A]]  = Map[ZRef[A, ZScopeType], Set[ZRef[A, ZObject]]]
  type IScopeMap    = ScopeMap[IA]
  type MScopeMap    = ScopeMap[MA]

  // FieldSet structures
  type FieldSeq[A <: Accessor[A]]  = Seq[ZRef[A, ZField]]
  type IFieldSeq                   = FieldSeq[IA]
  type MFieldSeq                   = FieldSeq[MA]

}
