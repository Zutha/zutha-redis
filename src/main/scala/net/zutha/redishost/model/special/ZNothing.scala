package net.zutha.redishost.model.special

import net.zutha.redishost.model.fieldclass._
import net.zutha.redishost.model.itemclass._
import net.zutha.redishost.model.`trait`.ZFieldDeclarer
import net.zutha.redishost.model.Referenceable

sealed trait ZNothing
  extends ZObject
  with ZField
  with ZItem
    with ZEntity
    with ZType
      with ZDatatype
      with ZScopeType
      with ZFieldMemberType
        with ZRole
        with ZLiteralType
      with ZObjectType
        with ZTrait
          with ZFieldDeclarer
        with ZClass
          with ZFieldClass
          with ZItemClass
  with Referenceable[ZObject with ZField with ZItem with ZEntity with ZType
    with ZDatatype with ZScopeType with ZFieldMemberType with ZRole
    with ZLiteralType with ZObjectType with ZTrait with ZFieldDeclarer
    with ZClass with ZFieldClass with ZItemClass]



