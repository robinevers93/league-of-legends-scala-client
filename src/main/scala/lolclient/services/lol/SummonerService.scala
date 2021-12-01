package lolclient.services.lol

import sttp.client3._
import sttp.model.Uri
import zio._
import zio.blocking._
import lolclient.LOLRequest.BaseSttpRequest
import lolclient.backend.ZIOBackend.ZIOSttpBackend
import lolclient.models.summoner.Summoner
import lolclient.services.lol.ReadServiceResponse._

final class SummonerService(
  backend: ZIOSttpBackend,
  baseUrl: Uri,
  getBaseRequest: RIO[Blocking, BaseSttpRequest]
) {

  def getSummoner(name: String): RIO[Blocking, Response[Either[String, Summoner]]] =
    getBaseRequest.flatMap { _
      .get(uri"$baseUrl/lol/summoner/v4/summoners/by-name/$name")
      .response(asLOLJson[Summoner])
      .send(backend)
    }
}