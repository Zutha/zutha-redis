package net.zutha.redishost.model

case class ZFieldDiff( addedRolePlayers: RolePlayerSet,
                       removedRolePlayers: RolePlayerSet,
                       addedLiterals: LiteralSet,
                       removedLiterals: LiteralSet,
                       modifiedLiterals: LiteralSet
                       )
