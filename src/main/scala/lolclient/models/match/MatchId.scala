package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class MatchId(id: String)

object MatchId {
  implicit val format: Format[MatchId] = Json.format[MatchId]
}

