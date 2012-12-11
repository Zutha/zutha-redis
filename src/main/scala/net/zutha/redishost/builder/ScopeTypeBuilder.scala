package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor

class ScopeTypeBuilder( val ref: MRef[ZScopeType] )
                      ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[ScopeTypeBuilder]
{

}
