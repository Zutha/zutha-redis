package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass.ZItemClass

class ItemClassBuilder( ref: MRef[ZItemClass] )
                      ( implicit override val acc: MutableAccessor )
  extends ClassBuilder( ref )
  with Builder[ItemClassBuilder]
{

}
