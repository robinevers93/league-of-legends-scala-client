package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class Metadata(
  dataVersion: String,
  matchId: String,
  participants: List[String]
)

object Metadata {
  implicit val format: Format[Metadata] = Json.format[Metadata]
}