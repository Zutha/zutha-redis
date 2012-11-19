package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MScopeType
import net.zutha.redishost.db.MutableAccessor

class ScopeTypeBuilder( ref: MRef[MScopeType] )( implicit val acc: MutableAccessor )
  extends ItemBuilder
{

}
