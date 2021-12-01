package lolclient.utils

import play.api.libs.json._
import zio.test._

object PlayJsonUtilsTest extends DefaultRunnableSpec {
  def spec = suite("Play json utils tests")(

    test("readsBy") {

      val result: Int = PlayJsonUtils
        .readsBy[Int, String](_.toIntOption.toRight("Boom!"))
        .reads(JsString("123"))
        .get

      assertTrue(result == 123)
    },

    test("writesBy") {

      val result = PlayJsonUtils
        .writesBy[Int, String](_.toString)
        .writes(123)

      assertTrue(result == JsString("123"))
    }
  )
}