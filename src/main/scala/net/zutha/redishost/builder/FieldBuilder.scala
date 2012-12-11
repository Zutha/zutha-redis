package net.zutha.redishost.builder

import net.zutha.redishost.model.MRef
import net.zutha.redishost.db.MutableAccessor

class FieldBuilder( val ref: MRef[ZField] )
                  ( implicit val acc: MutableAccessor )
  extends ObjectBuilder
  with Builder[FieldBuilder]
{

}
