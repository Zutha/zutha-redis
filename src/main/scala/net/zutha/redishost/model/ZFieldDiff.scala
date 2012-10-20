package net.zutha.redishost.model

import net.zutha.redishost.db.MutableAccessor

case class ZFieldDiff( addedRolePlayers: MRolePlayerSet,
                                              removedRolePlayers: MRolePlayerSet,
                                              addedLiterals: MLiteralSet,
                                              removedLiterals: MLiteralSet,
                                              modifiedLiterals: MLiteralSet
                                              )
