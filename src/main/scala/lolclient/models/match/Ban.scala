package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class Ban (
  championId: Int,
  pickTurn: Int
)

object Ban {
  implicit val format: Format[ObjectiveKills] = Json.format[ObjectiveKills]
}
