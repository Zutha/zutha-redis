package net.zutha.redishost.model

class Zid(val identifier: String) extends Ordered[Zid] {
  def compare(x: Zid, y: Zid) = x.identifier < y.identifier

  def compare(that: Zid) = this.identifier compare that.identifier
}
