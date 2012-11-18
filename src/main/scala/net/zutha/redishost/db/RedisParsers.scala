package net.zutha.redishost.db

import com.redis.serialization.Parse
import net.zutha.redishost.model.Zid

trait RedisParsers {
  implicit val parseZid = Parse[Zid]{ new String(_, "UTF-8") match {
    case Zid(zid) => zid
    case str => throw new Exception("Zid parse failure for string: " + str)
  }}
  implicit val parseString = Parse[String](new String(_, "UTF-8"))
}
