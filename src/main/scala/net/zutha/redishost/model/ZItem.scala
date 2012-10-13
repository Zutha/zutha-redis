package net.zutha.redishost.model

trait ZItem extends ZObject {

  def id: ZItemIdentity
  def zClass: ZItemClass

	def save() {}

}
