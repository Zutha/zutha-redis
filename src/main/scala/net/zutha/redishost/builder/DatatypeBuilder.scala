package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MDatatype
import net.zutha.redishost.db.MutableAccessor

class DatatypeBuilder( val ref: MRef[MDatatype] )( implicit val acc: MutableAccessor )
  extends ItemBuilder
{

}
