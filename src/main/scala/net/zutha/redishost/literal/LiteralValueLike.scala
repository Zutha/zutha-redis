package net.zutha.redishost.literal

import net.zutha.redishost.model.companion.ZDatatypeCompanion

trait LiteralValueLike[ +This <: LiteralValue ]
{
  protected def datatypeFactory: ZDatatypeCompanion[This]
}
