package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass.ZLiteralType

class LiteralTypeBuilder( val ref: MRef[ZLiteralType] )
                        ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[LiteralTypeBuilder]
{

}
