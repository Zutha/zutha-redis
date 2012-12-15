package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass.ZFieldClass

class FieldClassBuilder( ref: MRef[ZFieldClass] )
                       ( implicit override val acc: MutableAccessor )
  extends ClassBuilder( ref )
  with Builder[FieldClassBuilder]
{

}
