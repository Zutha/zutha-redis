package net.zutha.redishost.model

sealed trait ZIdentity
sealed trait PersistedId extends ZIdentity

sealed case class Zids(zid: Zid, allZids: List[Zid])
  extends PersistedId

sealed case class MZids(primaryZids: List[Zid], allZids: List[Zid])
  extends PersistedId

sealed case class TempId(id: Int)
  extends ZIdentity



