package net.zutha.redishost

import db.{MutableAccessor, DB}
import model._
import model.MRef

//import db.DB

object TestApp extends App {

  val r: MRole = ???
  val rref: MRef[MRole] = r.ref

  implicit def acc: MutableAccessor = ???
  val tRef = ZTrait.refM
  val ic: MItemClass = tRef.get
}

