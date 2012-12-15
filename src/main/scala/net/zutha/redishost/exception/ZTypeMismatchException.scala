package net.zutha.redishost.exception

class ZTypeMismatchException(msg: String, cause: Throwable) extends SchemaException(msg, cause){
  def this( msg: String ) = this( msg, null )
}
