package util

import org.joda.time.DateTime
import play.api.libs.json.{JodaReads, JodaWrites, Reads, Writes}

object JodaJson {
  implicit val jodaReads: Reads[DateTime] = JodaReads.DefaultJodaDateTimeReads
  implicit val jodaWrites: Writes[DateTime] = JodaWrites.JodaDateTimeNumberWrites
}
