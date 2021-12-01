package examles.lolclient

import lolclient.LOLConfig
import lolclient.models.`match`.Match
import lolclient.services.auth.AuthToken
import lolclient.services.lol.LOLService
import lolclient.syntax.LOLSyntax._
import sttp.client3.Response
import zio.RIO
import zio.blocking.Blocking

object MatchExample extends App {

  val config: LOLConfig = LOLConfig("api.riotgames.com", "europe", "euw1", None)
  val authConfig = AuthToken("RGAPI-0cb359db-f03c-45a3-a75d-46bf8b5e70a5")
  val puuid: String = "Ur5M1914aAHC8MrR5UNPL0wrCz8Fq6-pMPTxaKrNgxGdXjYooCX2mxRl4SjM1MqMtBQ0nadZ24d4wA"

  val matchService = LOLService.withConfig(config, authConfig).matchService

  {
    val listMatches: RIO[Blocking, Response[Either[String, List[String]]]] =
      matchService.listMatches(puuid, 3)

    val matches: List[String] = listMatches.getOrFail.unsafeRun
    println(matches)


    val getMatch: RIO[Blocking, Response[Either[String, Match]]] =
      matchService.getMatch(matches.head)

    val matchData: Match = getMatch.getOrFail.unsafeRun
    println(matchData)
  }

}