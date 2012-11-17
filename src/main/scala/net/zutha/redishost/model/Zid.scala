package net.zutha.redishost.model

import net.zutha.redishost.lib.BaseX

object Zid {
  private val ValidateChars = """\A([^\WIOSZa-z_0][^\WIOSZa-z_]+)\z""".r
  val charset = "0123456789ABCDEFGHJKLMNPQRTUVWXY"
  val base32 = BaseX(charset)

  def apply(zid: String) = {
    val correctZid = zid match{
      case Zid(repairedZID) => repairedZID
      case _ => throw new IllegalArgumentException
    }
    val hostIdLength = Zid.charset.indexOf(correctZid(0)) + 1
    val hostId = base32.decode( correctZid.substring(0,hostIdLength) )
    val identifier = base32.decode( correctZid.substring(hostIdLength + 1) )
    new Zid(correctZid, hostId.toInt, identifier)
  }

  def apply(hostId: Int, identifier: Int) = {
    val hostStr = base32.encode(hostId)
    val idStr = base32.encode(identifier)
    new Zid(hostStr+idStr, hostId, identifier)
  }

  /**
   * repairs the syntax of a ZID if it is valid
   * @param maybeZID string form of a ZID to try to resolve to a valid ZID
   * @return correctedID
   */
  def repair(maybeZID: String): Option[String] = correctCharset(maybeZID) match {
    case ValidateChars(zid) => {
      val hostIdLen = charset.indexOf(zid(0)) + 1
      if(zid.length > hostIdLen)
        Some(zid)
      else {
        None //the id is too short
      }
    }
    case _ => None //the id contains invalid characters
  }

  /**
   * extracts the corrected-syntax form of a ZID if it is valid
   * @param maybeZID string form of a ZID to try to resolve to a valid ZID
   * @return correctedID
   */
  def unapply(maybeZID: String): Option[String] = repair(maybeZID)

  /**
   * extracts the string representation of a ZID object
   * @return (ZID_string)
   */
  def unapply(zid: Zid): Option[String] = Some(zid.toString)

  private def correctCharset(zid: String) = {
    zid.toUpperCase.replace('I','1').replace('O', '0').replace('S','5').replace('Z','2')
  }
}

class Zid private (idStr: String, val hostId: Int, val identifier: Long)
  extends Ordered[Zid]
{
  def compare(that: Zid) = Ordering.Tuple2[Int, Long].compare( hostId -> identifier, that.hostId -> that.identifier )
  override def toString = idStr
  def toDec: String = s"${hostId}$identifier}"
  def toInt: Int = toDec.toInt
}
