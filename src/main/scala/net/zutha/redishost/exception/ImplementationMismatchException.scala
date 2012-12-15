package net.zutha.redishost.exception

class ImplementationMismatchException( msg: String, cause: Throwable = null )
  extends SchemaException( msg, cause )
{
  def this() = this( "" )
}
