package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model.singleton.ZDatatypeSingleton

trait LiteralValueLike[ +This <: LiteralValue ]
{
  protected def datatypeFactory: ZDatatypeSingleton[This]
}
