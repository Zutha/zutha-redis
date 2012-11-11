package net.zutha.redishost.model

object MsgType extends Enumeration {
  type MsgType = Value
  val Alert, Warning, Error = Value
}
