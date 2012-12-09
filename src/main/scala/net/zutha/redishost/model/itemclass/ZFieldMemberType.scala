package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.companion.ZItemClassCompanion
import net.zutha.redishost.model._

object ZFieldMemberType extends ZItemClassCompanion[ZFieldMemberType, IFieldMemberType, MFieldMemberType] {

  def name = "FieldMemberType"
}

trait ZFieldMemberType
  extends ZType
  with ZItemLike[ZFieldMemberType]

trait IFieldMemberType
  extends ZFieldMemberType
  with IType
  with IItemLike[IFieldMemberType]

trait MFieldMemberType
  extends ZFieldMemberType
  with MType
  with MItemLike[MFieldMemberType]
