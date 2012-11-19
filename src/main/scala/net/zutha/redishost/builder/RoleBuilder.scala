package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MRole
import net.zutha.redishost.db.MutableAccessor

class RoleBuilder( val ref: MRef[MRole] )( implicit val acc: MutableAccessor )
  extends ItemBuilder
{

}
