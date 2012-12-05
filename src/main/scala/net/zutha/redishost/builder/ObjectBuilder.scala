package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.MField
import net.zutha.redishost.model.itemclass.MObject

trait ObjectBuilder extends Builder[ObjectBuilder]
{
  implicit def acc: MutableAccessor
  def ref: MRef[MObject]

}
