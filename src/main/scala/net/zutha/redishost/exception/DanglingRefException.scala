package net.zutha.redishost.exception

import net.zutha.redishost.model.Ref

class DanglingRefException( ref: Ref[_, _], cause: Throwable = null)
  extends SchemaException(s"The underlying ZObject of $ref could not be found", cause)