package lolclient.services.lol

import lolclient.LOLRequest.BaseSttpRequest
import lolclient.backend.ZIOBackend.ZIOSttpBackend
import lolclient.models.`match`.Match
import lolclient.services.lol.ReadServiceResponse._
import sttp.client3._
import sttp.model.Uri
import zio._
import zio.blocking._

final class MatchService(
  backend: ZIOSttpBackend,
  baseUrl: Uri,
  getBaseRequest: RIO[Blocking, BaseSttpRequest]
) {

  def listMatches(puuid: String, count: Int): RIO[Blocking, Response[Either[String, List[String]]]] =
    getBaseRequest.flatMap { _
      .get(uri"$baseUrl/lol/match/v5/matches/by-puuid/$puuid/ids?count=$count")
      .response(asLOLJson[List[String]])
      .send(backend)
    }

  def getMatch(matchId: String): ZIO[Blocking, Throwable, Response[Either[String, Match]]] =
    getBaseRequest.flatMap { _
      .get(uri"$baseUrl/lol/match/v5/matches/$matchId")
      .response(asLOLJson[Match])
      .send(backend)
    }
}