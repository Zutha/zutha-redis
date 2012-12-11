package net.zutha.redishost


package object model {
  import itemclass._
  import fieldclass._
  import scala.language.implicitConversions

  // --- Type Constructors for Mutability ---

  // Object Type Constructors
  type ZObjT[+T <: ZObject]   = T with ZObjectLike[T]
  type I[+T <: ZObject]       = T with IObjectLike[T]
  type M[+T <: ZObject]       = T with MObjectLike[T]
  type IObject = I[ZObject]
  type MObject = M[ZObject]

  // Item Type Constructors
  type II[+T <: ZItem] = T with IItemLike[T]
  type MI[+T <: ZItem] = T with MItemLike[T]
  type IItem = II[ZItem]
  type MItem = MI[ZItem]

  // Field Type Constructors
  type IF[+T <: ZField] = T with IFieldLike[T]
  type MF[+T <: ZField] = T with MFieldLike[T]
  type IField = IF[ZField]
  type MField = MF[ZField]

  // Ref Constructor
  type RefU[U <: ZObject, T <: ZObject] = Ref[U, T with U]

  // Ref Aliases
  type ZRef[+T <: ZObject] = Ref[ZObject, T]

  type IRef[+T <: ZObject] = Ref[IObject, I[T]]
  type MRef[+T <: ZObject] = Ref[MObject, M[T]]
  type IIRef[+T <: ZItem]  = Ref[IObject, II[T]]
  type MIRef[+T <: ZItem]  = Ref[MObject, MI[T]]
  type IFRef[+T <: ZField] = Ref[IObject, IF[T]]
  type MFRef[+T <: ZField] = Ref[MObject, MF[T]]

  type ScopeSeq[B <: ZObjT]  = Seq[(Ref[B, ZScopeType], Seq[Ref[B, ZObject]])]
  type ScopeMap[B <: ZObjT]  = Map[Ref[B, ZScopeType], Set[Ref[B, ZObject]]]
  implicit def ScopeSeqToMap[B <: ZObjT]( scope: ScopeSeq[B] ): ScopeMap[B] = scope.toMap.mapValues(_.toSet)
  type IScopeSeq               = Seq[(IRef[ZScopeType], Seq[IRef[ZScopeType]])]
  type IScopeMap                = Map[IRef[ZScopeType], Set[IRef[ZScopeType]]]
  implicit def IScopeSeqToMap( scope: IScopeSeq ): IScopeMap = scope.toMap.mapValues(_.toSet)
  type MScopeSeq               = Seq[(MRef[ZScopeType], Seq[MRef[ZObject]])]
  type MScopeMap                = Map[MRef[ZScopeType], Set[MRef[ZObject]]]
  implicit def MScopeSeqToMap( scope: MScopeSeq ): MScopeMap = scope.toMap.mapValues(_.toSet)


  type FieldSeq[B <: ZObjT]  = Seq[Ref[B, ZField]]
  type IFieldSeq             = Seq[IRef[ZField]]
  type MFieldSeq             = Seq[MRef[ZField]]

}
