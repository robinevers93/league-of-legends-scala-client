package lolclient.models.summoner

import play.api.libs.json._

case class Summoner(
  id: String,
  accountId: String,
  puuid: String,
  name: String,
  profileIconId: Int,
  revisionDate: Long,
  summonerLevel: Int)

object Summoner {
  implicit val format: Format[Summoner] = Json.format[Summoner]
}
