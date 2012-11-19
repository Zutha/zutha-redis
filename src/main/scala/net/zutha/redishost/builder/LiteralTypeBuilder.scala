package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MLiteralType
import net.zutha.redishost.db.MutableAccessor

class LiteralTypeBuilder( val ref: MRef[MLiteralType] )( implicit val acc: MutableAccessor )
  extends ItemBuilder
{

}
