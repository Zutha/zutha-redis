package net.zutha.redishost

import db.{MutableAccessor, DB}
import model._
import model.MRef

//import db.DB

object TestApp extends App {

  val r: MRole = ???
  val rref: MRef[MRole] = r.ref

  implicit def acc: MutableAccessor = ???

  val tRef: MRef[MItemClass] = ZTrait
  val ic: MItemClass = tRef.get
}

