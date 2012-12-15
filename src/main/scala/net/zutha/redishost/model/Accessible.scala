package net.zutha.redishost.model

import net.zutha.redishost.db.Accessor

trait Accessible[Acc <: Accessor[Acc]] {

  type A = Acc
}
