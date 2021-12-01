package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class Team (
  bans: List[Ban],
  objectives: Objectives,
  teamId: Int,
  win: Boolean
)

object Team {
  implicit val banFormat: Format[Ban] = Json.format[Ban]
  implicit val format: Format[Team] = Json.format[Team]
}