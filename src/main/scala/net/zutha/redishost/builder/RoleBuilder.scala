package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor

class RoleBuilder( val ref: MRef[ZRole] )
                 ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[RoleBuilder]
{

}
