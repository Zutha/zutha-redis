package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.model.itemclass.MTrait
import net.zutha.redishost.db.MutableAccessor
import net.zutha.redishost.model.fieldclass.MField

class FieldBuilder( val ref: MRef[MField] )
                  ( implicit val acc: MutableAccessor )
  extends ObjectBuilder
  with Builder[FieldBuilder]
{

}
