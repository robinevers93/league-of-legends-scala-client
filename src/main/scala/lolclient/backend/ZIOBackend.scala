package lolclient.backend

import sttp.capabilities.WebSockets
import sttp.capabilities.zio.ZioStreams
import sttp.client3.SttpBackend
import sttp.client3.asynchttpclient.zio.AsyncHttpClientZioBackend
import zio.{Runtime, Task}

private[lolclient] object ZIOBackend {

  type ZIOSttpBackend = SttpBackend[Task, ZioStreams with WebSockets]

  val backend: SttpBackend[Task, ZioStreams with WebSockets] =
    Runtime.default.unsafeRun(AsyncHttpClientZioBackend())
}
