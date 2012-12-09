package net.zutha.redishost.model.fieldset

import net.zutha.redishost.model._
import fieldmember.{MLiteralMap, MRolePlayer}

case class ZFieldDiff( addedRolePlayers: Set[MRolePlayer],
                       removedRolePlayers: Set[MRolePlayer],
                       modifiedLiterals: MLiteralMap
                       )
