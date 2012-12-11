package net.zutha.redishost.model

import net.zutha.redishost.lib.BaseX

object Zid {
  private val ValidateChars = """\A([^\WIOSZa-z_0][^\WIOSZa-z_]+)\z""".r
  val charset = "0123456789ABCDEFGHJKLMNPQRTUVWXY"
  val base32 = BaseX(charset)

  def apply(zid: String) = {
    val repairedZid = repair(zid) match{
      case Some(repaired) => repaired
      case _ => throw new IllegalArgumentException
    }
    makeFromZidStr(repairedZid)
  }

  private def makeFromZidStr(zid: String) = {
    val hostIdLength = Zid.charset.indexOf(zid(0)) + 1
    val hostId = base32.decode( zid.substring(0,hostIdLength) )
    val identifier = base32.decode( zid.substring(hostIdLength + 1) )
    new Zid(zid, hostId.toInt, identifier)
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
        None //the zid is too short
      }
    }
    case _ => None //the zid contains invalid characters
  }

  /**
   * extracts the corrected-syntax form of a ZID if it is valid
   * @param maybeZID string form of a ZID to try to resolve to a valid ZID
   * @return a Some(Zid) if valid, otherwise None
   */
  def unapply(maybeZID: String): Option[Zid] = repair(maybeZID).map(makeFromZidStr(_))

  private def correctCharset(zid: String) = {
    zid.toUpperCase.replace('I','1').replace('O', '0').replace('S','5').replace('Z','2')
  }
}

class Zid private (idStr: String, val hostId: Int, val identifier: Long)
  extends Ordered[Zid]
{
  def compare(that: Zid) = Ordering.Tuple2[Int, Long].compare( hostId -> identifier, that.hostId -> that.identifier )

  def key = idStr

  override def toString = idStr

  def toDec: String = s"${hostId}$identifier}"
  
  def toInt: Int = toDec.toInt
}
