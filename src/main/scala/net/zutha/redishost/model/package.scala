package net.zutha.redishost



package object model {
  import itemclass._
  import fieldclass._
  import scala.language.implicitConversions

  type I = IObject
  type M = MObject

  type ScopeSeq                = Seq[(ZRef[ZScopeType], Seq[ZRef[ZObject]])]
  type ScopeMap                 = Map[ZRef[ZScopeType], Set[ZRef[ZObject]]]
  implicit def ScopeSeqToMap( scope: ScopeSeq ): ScopeMap = scope.toMap.mapValues(_.toSet)
  type IScopeSeq               = Seq[(IRef[IScopeType], Seq[IRef[IObject]])]
  type IScopeMap                = Map[IRef[IScopeType], Set[IRef[IObject]]]
  implicit def IScopeSeqToMap( scope: IScopeSeq ): IScopeMap = scope.toMap.mapValues(_.toSet)
  type MScopeSeq               = Seq[(MRef[MScopeType], Seq[MRef[MObject]])]
  type MScopeMap                = Map[MRef[MScopeType], Set[MRef[MObject]]]
  implicit def MScopeSeqToMap( scope: MScopeSeq ): MScopeMap = scope.toMap.mapValues(_.toSet)

  type FieldSeq            = Seq[ZRef[ZField]]
  type IFieldSeq           = Seq[IRef[IField]]
  type MFieldSeq           = Seq[MRef[MField]]

}
