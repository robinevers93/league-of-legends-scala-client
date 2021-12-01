package lolclient.models.`match`

import play.api.libs.json.{Format, Json}

case class Info (
  gameCreation: Long,
  gameDuration: Long,
  gameId: Long,
  gameMode: String,
  gameName: String,
  gameStartTimestamp: Long,
  gameType: String,
  gameVersion: String,
  mapId: Int,
  participants: List[Participant],
  platformId: String,
  queueId: Int,
  teams: List[Team],
  tournamentCode: String
)

object Info {
  implicit val format: Format[Info] = Json.format[Info]
}
