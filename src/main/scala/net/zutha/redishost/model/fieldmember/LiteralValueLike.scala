package net.zutha.redishost.model.fieldmember

import net.zutha.redishost.model.singleton.ZDatatypeSingleton
import net.zutha.redishost.db.Accessor
import net.zutha.redishost.model.ZRef
import net.zutha.redishost.model.itemclass.ZDatatype

trait LiteralValueLike[ +This <: LiteralValue ]
{
  protected def datatypeFactory: ZDatatypeSingleton[This]

  def datatype[A <: Accessor[A]]( implicit acc: A ): ZRef[A, ZDatatype] = datatypeFactory.ref[A, ZDatatype]
}
