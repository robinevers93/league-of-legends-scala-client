package lolclient.services.lol

import lolclient.LOLConfig
import lolclient.services.auth.AuthToken
import sttp.client3.Response
import sttp.model._

import scala.collection.immutable.Seq

object ClientUtils {
  val authConfig: AuthToken = AuthToken("RGAPI-fce073e6-33f4-4f08-bd42-fe34d488714a")
  val config: LOLConfig = LOLConfig("api.riotgames.com", "europe", "euw1")
}

object ResponseUtils {

  def successResponse(data: String): Response[String]= Response(
    data,
    StatusCode.Ok,
    "OK",
    Seq(Header("Content-Type", "application/json"))
  )

  def failResponse(errorMsg: String): Response[String] = Response(
    s"""
       |{
       |"data": {},
       |"success": false,
       |"message": "$errorMsg",
       |"api": { }
       |}
       |""".stripMargin,
    StatusCode.Ok,  // TODO: check what statuses are actually returned for failures
    "OK",
    Seq(Header("Content-Type", "application/json"))
  )

}
