package lolclient.services.lol

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.time._
import org.scalatest.wordspec.AnyWordSpec

trait LOLServiceSpec extends AnyWordSpec with ScalaFutures with Matchers {
  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(timeout =  Span(2, Seconds), interval = Span(5, Millis))
}
