package net.zutha.redishost.db

import net.zutha.redishost.model.{ZItem, ZRole, ZField}

protected[db] trait RedisUpdateQueries {

  // Note: all these methods should be protected[db].
  // They should only be used by an Accessor to commit changes to its database
  // An ImmutableAccessor will only ever receive update instructions from a MutableAccessor


}
