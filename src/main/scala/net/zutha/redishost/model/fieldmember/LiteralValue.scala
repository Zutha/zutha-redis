package net.zutha.redishost.model.fieldmember


trait LiteralValue
  extends LiteralValueLike[LiteralValue]
{


  def asString: String
}
