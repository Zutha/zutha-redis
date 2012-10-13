package net.zutha.redishost.model

import java.util.UUID

sealed trait ZIdentity
sealed trait ZPersistedIdentity extends ZIdentity
sealed trait ZItemIdentity extends ZIdentity
sealed trait ZFieldIdentity extends ZIdentity

sealed case class TempId(uuid: UUID) extends ZItemIdentity with ZFieldIdentity{
  def this() = this(UUID.randomUUID())
}

sealed case class Zids(primaryZids: Set[Zid], allZids: Set[Zid])
  extends ZPersistedIdentity with ZItemIdentity

sealed case class SingleZid(zid: Zid)
  extends ZPersistedIdentity with ZFieldIdentity