package net.zutha.redishost.exception

class SchemaException(msg: String, cause: Throwable) extends ZuthaException(msg, cause){
  def this( msg: String ) = this( msg, null )
}