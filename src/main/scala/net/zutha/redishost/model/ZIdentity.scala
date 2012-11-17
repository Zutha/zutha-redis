package net.zutha.redishost.model

sealed trait ZIdentity {
  def key: String
}
sealed trait PersistedId extends ZIdentity

sealed case class Zids(zid: Zid, allZids: List[Zid])
  extends PersistedId {

  def key = zid.toString
}

/**
 * Merged Zids (Identity for an object that has been merged)
 * @param primaryZids the primary Zid of each object that has been merged into this one
 * @param allZids the union of Zids of each merged object
 */
sealed case class MZids(primaryZids: List[Zid], allZids: List[Zid])
  extends PersistedId {

  def key = primaryZids.head.toString
}

sealed case class TempId(id: String)
  extends ZIdentity {

  def key = id
}



