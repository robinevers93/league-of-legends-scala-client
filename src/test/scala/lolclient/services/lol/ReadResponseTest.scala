package lolclient.services.lol

import play.api.libs.json._
import sttp.client3._
import sttp.client3.playJson._
import sttp.model.StatusCode
import zio.test.Assertion._
import zio.test._

import lolclient.services.lol.ReadServiceResponse._

object ReadResponseTest extends DefaultRunnableSpec {

  final case class Data(data: String, message: Option[String])
  object Data {
    implicit val format: Format[Data] = Json.format[Data]
  }

  final case class BadData(data: Int, message: Option[String])
  object BadData {
    implicit val format: Format[BadData] = Json.format[BadData]
  }

  def spec = suite("Read RML http response tests")(

    test("Read 500 error") {

      val errorMsg = "Boom!"
      val response = HttpError(Json.toJson(Data("foo", Some(errorMsg))), StatusCode.InternalServerError)

      val assertion =
        isLeft(containsString(errorMsg)) &&
        isLeft(containsString(StatusCode.InternalServerError.code.toString))

      assert(readResponseOpt[String](Left(response)))(assertion)
    },

    test("Read Not Found - returns an empty option") {
      val errorMsg = "Not found"
      val response = HttpError(Json.toJson(Data("foo", Some(errorMsg))), StatusCode.NotFound)
      assert(readResponseOpt[String](Left(response)))(isRight(isNone))
    },

    test("Read Not Found - returns an error") {

      val errorMsg = "Not found"
      val response = HttpError(Json.toJson(Data("foo", Some(errorMsg))), StatusCode.NotFound)

      val assertion =
        isLeft(containsString("not found")) &&
        isLeft(containsString(StatusCode.NotFound.code.toString))

      assert(readResponse[String](Left(response)))(assertion)
    },

    test("Read error") {
      val errorMsg = "Read error"
      val response = DeserializationException(errorMsg, JsError(errorMsg))
      assert(readResponseOpt[String](Left(response)))(isLeft(containsString("Error reading http response data")))
    },

    test("Read data") {
      val response = Json.toJson(Data("The rain in Spain", None))
      assert(readResponseOpt[String](Right(response)))(isRight(isSome(equalTo("The rain in Spain"))))
    }
  )
}