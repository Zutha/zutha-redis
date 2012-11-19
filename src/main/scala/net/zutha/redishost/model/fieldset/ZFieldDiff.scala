package net.zutha.redishost.model.fieldset

import net.zutha.redishost.model._
import fieldclass._
import itemclass._

case class ZFieldDiff( addedRolePlayers: MRolePlayerSet,
                       removedRolePlayers: MRolePlayerSet,
                       addedLiterals: MLiteralSet,
                       removedLiterals: MLiteralSet,
                       modifiedLiterals: MLiteralSet
                       )
