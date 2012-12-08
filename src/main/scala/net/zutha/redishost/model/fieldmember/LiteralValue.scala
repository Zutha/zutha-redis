package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model.itemclass.{IDatatype, MDatatype}
import net.zutha.redishost.db.{ImmutableAccessor, MutableAccessor}
import net.zutha.redishost.model.{MRef, IRef}

trait LiteralValue
  extends LiteralValueLike[LiteralValue]
{
  def datatypeI( implicit acc: ImmutableAccessor ): IRef[IDatatype] = datatypeFactory.refI
  def datatypeM( implicit acc: MutableAccessor ): MRef[MDatatype] = datatypeFactory.refM

  def asString: String
}
