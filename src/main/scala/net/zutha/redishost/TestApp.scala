package net.zutha.redishost

import db.DB
import model.{IRole, ZRole}

//import db.DB

object TestApp extends App {
  class Ref
  class Ref2 extends Ref

  trait C0 {
    type C <: C0
    type R <: Ref
    def id(thing: C): C = thing
    def ref: C
    def map: Map[R, C0]
  }
  class C1A extends C0 {
    type C <: C1A
    def ref: C = (new Ref).asInstanceOf[Ref with C]
    def map: Map[Ref2, C1A] = Map()

  }
  class C1B extends C0 {
    type C <: C1B
    def ref: C = (new Ref).asInstanceOf[Ref with C]
    def map: Map[Ref2, C1B] = Map()
  }
  trait C2A extends C1A {
    type C = C2A
  }

  val c2a = new C1A with C2A
  val c1b = new C1B
  val c0: C0 = c1b

  val c2a_ref: C2A = c2a.ref
  val c0_ref: C0 = c0.ref
  val c1b_ref: C1B = c1b.ref

  val c0_map: Map[Ref, C0] = c0.map


}

