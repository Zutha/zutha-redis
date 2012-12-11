package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model.{MRef, IRef}

trait LiteralValue
  extends LiteralValueLike[LiteralValue]
{
  def datatypeI( implicit acc: ImmutableAccessor ): IRef[ZDatatype] = datatypeFactory.refI
  def datatypeM( implicit acc: MutableAccessor ): MRef[ZDatatype] = datatypeFactory.ref

  def asString: String
}
