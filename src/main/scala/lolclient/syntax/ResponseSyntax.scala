package lolclient.syntax

import java.time.Duration

import scala.util.Try

import cats.implicits._
import sttp.client3.Response
import sttp.model.StatusCode
import zio._
import zio.clock.Clock
import zio.stream.ZStream

import lolclient.LOLException

private[syntax] trait ResponseSyntax {

  implicit class ResponseOps[A](response: Response[Either[String, A]]) {
    def getOrFail: Try[A] = response.body.leftMap(new LOLException(_)).toTry
  }
  
  implicit class ResponseTaskOps[R, A](task: RIO[R, Response[Either[String, A]]]) {

    def getOrFail: RIO[R, A] = task.map(_.getOrFail).flatMap(ZIO.fromTry(_))

    def withRetry(
      schedule: Schedule[Any, Any, Any],
      retryWhile: Response[Either[String, A]] => Boolean,
    ): RIO[R with Clock, Response[Either[String, A]]] = ZStream.repeatEffectWith(task, schedule)
      .takeWhile(retryWhile(_))
      .runLast
      .someOrFail(new RuntimeException("Error processing http request"))

    def withExpRetry(
      retryCount: Int,
      retryWhile: Response[Either[String, A]] => Boolean,
      initial: Duration
    ): RIO[R with Clock, Response[Either[String,A]]] = {
      val schedule = Schedule.exponential(initial) && Schedule.recurs(retryCount)
      withRetry(schedule, retryWhile)
    }

    def withExpRetryIfTooBusy(retryCount: Int, initial: Duration): RIO[R with Clock, Response[Either[String, A]]] =
      withExpRetry(retryCount, tooBusy, initial)
  }

  private def tooBusy[A](response: Response[Either[String, A]]): Boolean =
    response.code == StatusCode.TooManyRequests
}
