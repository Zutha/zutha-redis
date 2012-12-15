package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.itemclass.ZTrait

class TraitBuilder( val ref: MRef[ZTrait] )
                  ( implicit val acc: MutableAccessor )
  extends ItemBuilder
  with Builder[TraitBuilder]
{

}
