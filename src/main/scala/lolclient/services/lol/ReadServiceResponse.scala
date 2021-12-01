package lolclient.services.lol

import play.api.libs.json._
import sttp.client3._
import sttp.client3.playJson._
import sttp.model.StatusCode

private[lol] object ReadServiceResponse {

  val asUnit: ResponseAs[Either[String, Unit], Any] = asString.mapWithMetadata {
    case (Right(_), _) => Right(())
    case (Left(e), metadata) => if (metadata.code == StatusCode.NotFound) Right(()) else Left(e)
  }

  def readResponseOpt[A: Reads](response: Either[ResponseException[JsValue, JsError], JsValue]): Either[String, Option[A]] = response match {
    case Right(json) => readData(json).map(Option(_))
    case Left(HttpError(_, StatusCode.NotFound)) => Right(None)
    case Left(DeserializationException(_, _)) => Left(s"Error reading http response data")
    case Left(HttpError(body, statusCode)) => Left(readError(body, statusCode))
  }

  def readResponse[A: Reads](response: Either[ResponseException[JsValue, JsError], JsValue]): Either[String, A] = readResponseOpt[A](response) match {
    case Left(e) => Left(e)
    case Right(Some(a)) => Right(a)
    case Right(None) => Left(s"Http error status code ${StatusCode.NotFound} - resource not found")
  } 

  def asLOLJson[A: Reads]: ResponseAs[Either[String, A], Any] =
    asJsonEither[JsValue, JsValue].map(readResponse[A])

  def asLOLJsonOpt[A: Reads]: ResponseAs[Either[String, Option[A]], Any] =
    asJsonEither[JsValue, JsValue].map(readResponseOpt[A])

  private def readData[A: Reads](body: JsValue): Either[String, A] = body.asOpt[A]
    .toRight("Error reading http response data")

  private def readError(body: JsValue, statusCode: StatusCode): String = (body \ "status" \ "message")
    .toOption
    .collect { case JsString(msg) => s"Http error status code ${statusCode.code} - $msg" }
    .getOrElse(s"HTTP error status code ${statusCode.code}")
}