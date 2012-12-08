package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model.companion.ZDatatypeCompanion

trait LiteralValueLike[ +This <: LiteralValue ]
{
  protected def datatypeFactory: ZDatatypeCompanion[This]
}
