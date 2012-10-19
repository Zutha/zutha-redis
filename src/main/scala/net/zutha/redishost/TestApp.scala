package net.zutha.redishost

import db.DB
import model.{ZIRole, ZRole}

//import db.DB

object TestApp extends App {
  val a = DB.getImmutableAccessor
  val r: ZIRole[a.type] = ???
  r.ref.ref

}

