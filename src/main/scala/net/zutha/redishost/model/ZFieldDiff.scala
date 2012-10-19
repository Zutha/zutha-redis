package net.zutha.redishost.model

import net.zutha.redishost.db.MutableAccessor

case class ZFieldDiff[A <: MutableAccessor]( addedRolePlayers: MRolePlayerSet[A],
                                              removedRolePlayers: MRolePlayerSet[A],
                                              addedLiterals: MLiteralSet[A],
                                              removedLiterals: MLiteralSet[A],
                                              modifiedLiterals: MLiteralSet[A]
                                              )
