package net.zutha.redishost.builder

trait Builder[+T <: Builder[T]] { self: T =>
  def apply( f: T => Unit ): T = { f(this); this }
}
