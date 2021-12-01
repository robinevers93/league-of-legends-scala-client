package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class ObjectiveKills (
  first: Boolean,
  kills: Int
)

object ObjectiveKills {
  implicit val format: Format[ObjectiveKills] = Json.format[ObjectiveKills]
}

