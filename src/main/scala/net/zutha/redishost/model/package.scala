package net.zutha.redishost

package object model {

  type RolePlayer = (ZRole, ZObject)
  type RolePlayerSet = Set[RolePlayer]

  type Literal = (ZLiteralType, ZLiteralValue)
  type LiteralSet = Set[Literal]

  type FieldMap[T] = Map[ZFieldIdentity, T]
  type FieldSetMap[T] = Map[(ZRole, ZFieldClass), T]

}
