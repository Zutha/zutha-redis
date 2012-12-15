package net.zutha.redishost.exception

import net.zutha.redishost.model.ZRef

class DanglingRefException( msg: String, cause: Throwable = null )
  extends SchemaException(msg, cause)
{
  def this( ref: ZRef[_, _] ) = {
    this( s"The underlying ZObject of $ref could not be found" )
  }
}