package lolclient.syntax

import scala.concurrent.Future

import zio._
import zio.blocking.Blocking

private[syntax] trait ZIOSyntax {

  private val runtime = Runtime.default

  implicit class RunOps[A](task: ZIO[ZEnv, Throwable, A]) {
    def unsafeRun: A = runtime.unsafeRun(task)
    def runToFuture: Future[A] = runtime.unsafeRunToFuture(task)
  }

  implicit class BlockingOps[R, A](task: RIO[R, A]) {
    def asBlockingTask: RIO[R with Blocking, A] = zio.blocking.blocking(task)
  }
}

private[syntax] object ZIOSyntax extends ZIOSyntax