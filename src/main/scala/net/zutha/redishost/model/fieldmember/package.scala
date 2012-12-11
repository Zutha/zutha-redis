package net.zutha.redishost.model


package object fieldmember {
  import scala.language.implicitConversions

  type ILiteralMap = Map[IRef[ZLiteralType], LiteralValue]
  implicit def literalSetToMapI( set: Set[ILiteral] ): ILiteralMap = set.map(_.toPair).toMap

  type MLiteralMap = Map[MRef[ZLiteralType], LiteralValue]
  implicit def literalSetToMapM( set: Set[MLiteral] ): MLiteralMap = set.map(_.toPair).toMap
}
