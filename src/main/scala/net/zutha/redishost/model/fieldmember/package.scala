package net.zutha.redishost.model

import itemclass.ZLiteralType
import net.zutha.redishost.db.Accessor


package object fieldmember {
  import scala.language.implicitConversions

  type IFieldMember = FieldMember[IA]
  type MFieldMember = FieldMember[MA]

  type IRolePlayer = RolePlayer[IA]
  type MRolePlayer = RolePlayer[MA]

  type IRoleFieldMember = RoleFieldMember[IA]
  type MRoleFieldMember = RoleFieldMember[MA]

  type RolePlayerSet[A <: Accessor[A]] = Set[RolePlayer[A]]
  type MRolePlayerSet = Set[MRolePlayer]
  type IRolePlayerSet = Set[IRolePlayer]

  type ILiteralFieldMember = LiteralFieldMember[IA]
  type MLiteralFieldMember = LiteralFieldMember[MA]

  type LiteralMap[A <: Accessor[A]] = Map[ZRef[A, ZLiteralType], LiteralValue]
  implicit def literalSetToMap[A <: Accessor[A]]( set: Set[LiteralFieldMember[A]] ): LiteralMap[A] = set.map(_.toPair).toMap
  type ILiteralMap = Map[IRef[ZLiteralType], LiteralValue]
  type MLiteralMap = Map[MRef[ZLiteralType], LiteralValue]
//  implicit def literalSetToMapI( set: Set[ILiteralFieldMember] ): ILiteralMap = set.map(_.toPair).toMap
//  implicit def literalSetToMapM( set: Set[MLiteralFieldMember] ): MLiteralMap = set.map(_.toPair).toMap
}
