package net.zutha.redishost.lib

import scala.reflect.runtime.universe._

object ReflectionHelpers {

  def typeToString( tpe: Type ): String = tpe match {
    case rt: RefinedType => rt.toString
    case t => t.toString
  }
}
