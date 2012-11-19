package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MItemClass
import net.zutha.redishost.db.MutableAccessor

class ItemClassBuilder( ref: MRef[MItemClass] )( implicit override val acc: MutableAccessor )
  extends ClassBuilder( ref )
{

}
