package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MFieldClass
import net.zutha.redishost.db.MutableAccessor

class FieldClassBuilder( override val ref: MRef[MFieldClass] )( implicit override val acc: MutableAccessor )
  extends ClassBuilder( ref )
{

}
