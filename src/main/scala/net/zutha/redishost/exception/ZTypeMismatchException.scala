package net.zutha.redishost.exception

class ZTypeMismatchException( msg: String, cause: Throwable = null )
  extends SchemaException( msg, cause )
{
  def this() = this( "", null )
}
