package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.db.MutableAccessor

class ClassBuilder( val ref: MRef[MClass] )( implicit val acc: MutableAccessor )
  extends ItemBuilder
{

}
