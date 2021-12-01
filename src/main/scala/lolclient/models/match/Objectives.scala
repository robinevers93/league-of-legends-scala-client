package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class Objectives (
  baron: ObjectiveKills,
  champion: ObjectiveKills,
  dragon: ObjectiveKills,
  inhibitor: ObjectiveKills,
  riftHerald: ObjectiveKills,
  tower: ObjectiveKills
)

object Objectives {
  implicit val format: Format[Objectives] = Json.format[Objectives]
}
