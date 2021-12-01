package lolclient

import sttp.client3._
import sttp.model.Uri

import lolclient.LOLRequest.BaseSttpRequest

case class LOLConfig(baseUrl: String, bigRegion: String, smallRegion: String, host: Option[String] = None) {
  val summonerBaseUri: Uri = uri"https://$smallRegion.$baseUrl"
  val matchBaseUri: Uri = uri"https://$bigRegion.$baseUrl"
  val baseRequest: BaseSttpRequest = basicRequest.header("Host", host)
}
