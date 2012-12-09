package net.zutha.redishost.model

import itemclass.{MLiteralType, ILiteralType}

package object fieldmember {
  import scala.language.implicitConversions

  type ILiteralMap = Map[IRef[ILiteralType], LiteralValue]
  implicit def literalSetToMapI( set: Set[ILiteral] ): ILiteralMap = set.map(_.toPair).toMap

  type MLiteralMap = Map[MRef[MLiteralType], LiteralValue]
  implicit def literalSetToMapM( set: Set[MLiteral] ): MLiteralMap = set.map(_.toPair).toMap
}
