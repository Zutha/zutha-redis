package net.zutha.redishost.model.itemclass

import net.zutha.redishost.model.singleton.ZItemClassSingleton
import net.zutha.redishost.model._

object ZFieldMemberType extends ZItemClassSingleton[ZFieldMemberType, IFieldMemberType, MFieldMemberType] {

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
