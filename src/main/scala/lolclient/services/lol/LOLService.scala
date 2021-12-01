package lolclient.services.lol

import sttp.model.Header
import zio._
import zio.blocking._

import lolclient.LOLConfig
import lolclient.LOLRequest.BaseSttpRequest
import lolclient.backend.ZIOBackend
import lolclient.backend.ZIOBackend.ZIOSttpBackend
import lolclient.services.auth._

final class LOLService(
  config: LOLConfig,
  backend: ZIOSttpBackend,
  authConfig: AuthToken
) {

  private val getBaseRequest: RIO[Blocking, BaseSttpRequest] =
    ZIO.succeed(config.baseRequest.headers(Header("X-Riot-Token", authConfig.developmentApiKey)))


  val summonerService: SummonerService = new SummonerService(backend, config.summonerBaseUri, getBaseRequest)
  val matchService: MatchService = new MatchService(backend, config.matchBaseUri, getBaseRequest)

  def withAuth(authConfig: AuthToken): LOLService =
    new LOLService(config, backend, authConfig)
}

object LOLService {

  def apply(config: LOLConfig, token: AuthToken, backend: ZIOSttpBackend): LOLService =
    new LOLService(config, backend, token)

  def withConfig(config: LOLConfig, token: AuthToken): LOLService =
    new LOLService(config, ZIOBackend.backend, token)
}