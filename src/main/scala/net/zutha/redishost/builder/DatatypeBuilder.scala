package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass.ZDatatype

class DatatypeBuilder( val ref: MRef[ZDatatype] )
                     ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[DatatypeBuilder]
{

}
