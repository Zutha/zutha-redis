package net.zutha.redishost.exception

class ZuthaException(msg: String, cause: Throwable) extends RuntimeException(msg, cause){
  def this( msg: String ) = this( msg, null )
}