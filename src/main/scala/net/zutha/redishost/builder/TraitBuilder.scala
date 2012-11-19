package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MTrait
import net.zutha.redishost.db.MutableAccessor

class TraitBuilder( val ref: MRef[MTrait] )( implicit val acc: MutableAccessor )
  extends ItemBuilder
{

}
