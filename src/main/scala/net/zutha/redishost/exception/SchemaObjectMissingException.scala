package net.zutha.redishost.exception

class SchemaObjectMissingException( msg: String, cause: Throwable = null )
  extends SchemaException( msg, cause )
{
  def this() = this( "" )
}
