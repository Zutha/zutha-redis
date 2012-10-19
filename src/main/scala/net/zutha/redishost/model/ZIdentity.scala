package net.zutha.redishost.model

import java.util.UUID

sealed trait ZIdentity {
  def hashCode: Int
}
sealed trait ZPersistedIdentity extends ZIdentity
sealed trait ZMutableIdentity extends ZIdentity
sealed trait ZFieldIdentity extends ZIdentity

sealed case class TempId(uuid: UUID)
  extends ZMutableIdentity
  with ZFieldIdentity{

  def this() = this(UUID.randomUUID())
}

sealed case class Zids(primaryZids: Set[Zid], allZids: Set[Zid])
  extends ZPersistedIdentity
  with ZMutableIdentity

sealed case class Zid(identifier: String)
  extends Ordered[Zid]
  with ZPersistedIdentity
  with ZFieldIdentity
{
  def compare(x: Zid, y: Zid) = x.identifier < y.identifier

  def compare(that: Zid) = this.identifier compare that.identifier
}