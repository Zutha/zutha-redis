package net.zutha.redishost.model.fieldset

import net.zutha.redishost.model._
import fieldmember.{MLiteral, MRolePlayer}

case class ZFieldDiff( addedRolePlayers: Set[MRolePlayer],
                       removedRolePlayers: Set[MRolePlayer],
                       addedLiterals: Set[MLiteral],
                       removedLiterals: Set[MLiteral],
                       modifiedLiterals: Set[MLiteral]
                       )
