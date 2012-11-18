package net.zutha.redishost.exception

class SchemaObjectMissingException(msg: String, cause: Throwable) extends SchemaException(msg, cause){
  def this( msg: String ) = this( msg, null )
}
