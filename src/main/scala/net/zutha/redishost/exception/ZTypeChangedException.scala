package net.zutha.redishost.exception

class ZTypeChangedException( msg: String, cause: Throwable = null )
  extends SchemaException( msg, cause ){

  def this() = this( "", null )
}
