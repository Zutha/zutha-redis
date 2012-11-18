package net.zutha.redishost.db

object Lua {
  def apply( scriptName: String ): String = {
    val source = io.Source.fromFile(s"src/main/lua/$scriptName.lua")
    val text = source.mkString
    source.close()
    text
  }
}
