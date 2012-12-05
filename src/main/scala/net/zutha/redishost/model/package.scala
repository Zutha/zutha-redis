package net.zutha.redishost

import model.literal.LiteralValue

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

  type RolePlayer           = (ZRef[ZRole], ZRef[ZObject])
  type RolePlayerSet        = Set[RolePlayer]
  type RolePlayerMap        = Map[ZRef[ZRole], Set[ZRef[ZObject]]]
  implicit def RolePlayerSetToMap( rps: RolePlayerSet ): RolePlayerMap = rps.groupBy(_._1).mapValues(_.map(_._2))
  type IRolePlayer          = (IRef[IRole], IRef[IObject])
  type IRolePlayerSet       = Set[IRolePlayer]
  type IRolePlayerMap       = Map[IRef[IRole], Set[IRef[IObject]]]
  implicit def IRolePlayerSetToMap( rps: IRolePlayerSet ): IRolePlayerMap = rps.groupBy(_._1).mapValues(_.map(_._2))
  type MRolePlayer          = (MRef[MRole], MRef[MObject])
  type MRolePlayerSet       = Set[MRolePlayer]
  type MRolePlayerMap       = Map[MRef[MRole], Set[MRef[MObject]]]
  implicit def MRolePlayerSetToMap( rps: MRolePlayerSet ): MRolePlayerMap = rps.groupBy(_._1).mapValues(_.map(_._2))



  type Literal              = (ZRef[ZLiteralType], LiteralValue)
  type LiteralSet           = Set[Literal]
  type LiteralMap           = Map[ZRef[ZLiteralType], LiteralValue]
  implicit def LiteralSetToMap( literals: LiteralSet ): LiteralMap = literals.toMap
  type ILiteral             = (IRef[ILiteralType], LiteralValue)
  type ILiteralSet          = Set[ILiteral]
  type ILiteralMap          = Map[IRef[ILiteralType], LiteralValue]
  implicit def ILiteralSetToMap( literals: ILiteralSet ): ILiteralMap = literals.toMap
  type MLiteral             = (MRef[MLiteralType], LiteralValue)
  type MLiteralSet          = Set[MLiteral]
  type MLiteralMap          = Map[MRef[MLiteralType], LiteralValue]
  implicit def MLiteralSetToMap( literals: MLiteralSet ): MLiteralMap = literals.toMap

  type FieldSeq            = Seq[ZRef[ZField]]
  type IFieldSeq           = Seq[IRef[IField]]
  type MFieldSeq           = Seq[MRef[MField]]

}
