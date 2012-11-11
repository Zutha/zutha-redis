package net.zutha.redishost.model

object ScopeMatchType extends Enumeration {
  type ScopeMatchType = Value
  val UpperBound, LowerBound, Exact, Closest = Value
}