package net.zutha.redishost.model

import java.util.UUID

sealed trait ZIdentity
sealed trait PersistedId extends ZIdentity

sealed case class Zids(zid: Zid, allZids: List[Zid])
  extends PersistedId

sealed case class MZids(primaryZids: List[Zid], allZids: List[Zid])
  extends PersistedId

sealed case class Zid(identifier: String)
  extends Ordered[Zid]
{
  def compare(x: Zid, y: Zid) = x.identifier < y.identifier

  def compare(that: Zid) = this.identifier compare that.identifier
}

sealed case class TempId(uuid: UUID)
  extends ZIdentity
{
  def this() = this(UUID.randomUUID())
}