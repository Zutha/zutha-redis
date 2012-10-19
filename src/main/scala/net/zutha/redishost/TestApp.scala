package net.zutha.redishost

import db.DB
import model.{IRole, ZRole}

//import db.DB

object TestApp extends App {
  class Ref
  class Ref2 extends Ref
  val r2 = new Ref2

  trait C0 {
    type C <: C0
    type R <: Ref
    def id(thing: C): C = thing
    def ref: C
    def make: C0
    def map: Map[R, C0]
    def mapGet(r: R) = map(r)
  }
  class C1A extends C0 {
    type C <: C1A
    type R = Ref2

    def ref: C = (new Ref).asInstanceOf[Ref with C]
    def make: C1A = this

    def map: Map[R, C1A] = Map(r2 -> this)
  }
  class C1B extends C0 {
    type C <: C1B
    type R = Ref2

    def ref: C = (new Ref).asInstanceOf[Ref with C]
    def make: C1B = this
    def map: Map[Ref2, C1B] = Map(r2 -> this)
  }
  trait C2A extends C1A {
    type C = C2A
  }

  val c2a = new C1A with C2A
  val c1b = new C1B
  c1b.mapGet(r2)
  val c0: C0 = c1b

  val c2a_ref: C2A = c2a.ref
  val c0_ref: C0 = c0.ref
  val c1b_ref: C1B = c1b.ref



}

