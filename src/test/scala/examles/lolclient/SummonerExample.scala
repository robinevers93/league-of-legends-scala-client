package examles.lolclient

import sttp.client3.Response
import zio.RIO
import zio.blocking.Blocking
import lolclient.LOLConfig
import lolclient.models.summoner.Summoner
import lolclient.services.auth.AuthToken
import lolclient.services.lol.LOLService
import lolclient.syntax.LOLSyntax._

object SummonerExample extends App {

  val config: LOLConfig = LOLConfig("api.riotgames.com", "europe", "euw1", None)
  val authConfig = AuthToken("RGAPI-0cb359db-f03c-45a3-a75d-46bf8b5e70a5")
  val name: String = "Red Flying Robin"
  val fakeName: String = "Red Flying Robin 123"

  val summonerService = LOLService.withConfig(config, authConfig).summonerService

  {
    val getSummonerNonExistent: RIO[Blocking, Response[Either[String, Summoner]]] =
      summonerService.getSummoner(fakeName)

    println(getSummonerNonExistent.unsafeRun)
  }

  {
    val getSummonerCorrect: RIO[Blocking, Summoner] =
      summonerService.getSummoner(name).getOrFail

    println(getSummonerCorrect.unsafeRun)
  }


}