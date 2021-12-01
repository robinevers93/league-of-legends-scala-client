package lolclient.services.lol

import scala.collection.immutable.Seq
import sttp.capabilities.WebSockets
import sttp.capabilities.zio.ZioStreams
import sttp.client3.Response
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import sttp.client3.testing.SttpBackendStub
import sttp.model._
import zio._
import zio.duration._
import lolclient.LOLConfig
import lolclient.models.summoner.Summoner
import lolclient.services.auth.AuthToken
import lolclient.services.lol.SummonerServiceTest._
import lolclient.syntax.LOLSyntax._

class SummonerServiceTest extends LOLServiceSpec {

  "get summoner call" must {

    "succeed" in {
      val name = "Red Flying Robin"
      val robinSum: Summoner = Summoner(
        "VjcWLAUN1EkSZvYJMH_OeG1oPfulStXuWLlBAVCHVkpAo_s",
        "U4faGB30D-5Z3qvpQtTE0LTQf3fweMJ5pqLbfj8_0j2KiaM",
        "Ur5M1914aAHC8MrR5UNPL0wrCz8Fq6-pMPTxaKrNgxGdXjYooCX2mxRl4SjM1MqMtBQ0nadZ24d4wA",
        "Red Flying Robin",
        3478,
        1628893419000L,
        120)
      val summoner = LOLService(ClientUtils.config, ClientUtils.authConfig, summonerSuccessBackend).summonerService.getSummoner(name)
      assert(summoner.getOrFail.unsafeRun == robinSum)
    }

    "fail when using a non-existing user name" in {
      val fakeName = "dslkfji32jls"
      val summoner = LOLService(ClientUtils.config, ClientUtils.authConfig, nonExistentSummonerBackend).summonerService.getSummoner(fakeName)
      summoner.getOrFail.isFailure.unsafeRun shouldBe true
    }

    "be retried when rate limited" in {

      val name = "Red Flying Robin"
      val config = LOLConfig("api.riotgames.com", "europe", "euw1")
      val schedule = Schedule.spaced(java.time.Duration.ofMillis(200)) && Schedule.recurs(5)

      def tooBusy[A](response: Response[Either[String, A]]): Boolean = response.code == StatusCode.TooManyRequests

      val task = LOLService(config, AuthToken("123"), rateLimitedBackend)
        .summonerService.getSummoner(name)
        .withRetry(schedule, tooBusy)
        .timed
        
      val (duration, isFailure) = (for {
        (duration, response) <- task
        isFailure = response.getOrFail.isFailure
      } yield (duration, isFailure)).unsafeRun

      isFailure shouldBe true
      duration > Duration.fromMillis(1000)
    }
  }
}

object SummonerServiceTest {
  import ResponseUtils._

  val summonerSuccessBackend: SttpBackendStub[Task,ZioStreams with WebSockets] = AsyncHttpClientZioBackend.stub
    .whenAnyRequest
    .thenRespond(
      successResponse("""{
                        |    "id": "VjcWLAUN1EkSZvYJMH_OeG1oPfulStXuWLlBAVCHVkpAo_s",
                        |    "accountId": "U4faGB30D-5Z3qvpQtTE0LTQf3fweMJ5pqLbfj8_0j2KiaM",
                        |    "puuid": "Ur5M1914aAHC8MrR5UNPL0wrCz8Fq6-pMPTxaKrNgxGdXjYooCX2mxRl4SjM1MqMtBQ0nadZ24d4wA",
                        |    "name": "Red Flying Robin",
                        |    "profileIconId": 3478,
                        |    "revisionDate": 1628893419000,
                        |    "summonerLevel": 120
                        |}""".stripMargin)
    )

  val nonExistentSummonerBackend: SttpBackendStub[Task,ZioStreams with WebSockets] = AsyncHttpClientZioBackend.stub
    .whenAnyRequest
    .thenRespond(
      Response(
        "Data not found",
        StatusCode.NotFound,
        "summoner not found",
        Seq(Header("Content-Type", "text/plain"))
      )
    )

  val rateLimitedBackend: SttpBackendStub[Task,ZioStreams with WebSockets] = AsyncHttpClientZioBackend.stub
    .whenAnyRequest
    .thenRespond(
      Response(
        "Too many requests",
        StatusCode.TooManyRequests,
        "Too many requests"
      )
    )
}