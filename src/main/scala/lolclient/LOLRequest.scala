package lolclient

import sttp.client3._

private[lolclient] object LOLRequest {
  type BaseSttpRequest = RequestT[Empty, Either[String, String], Any]
  type RMLRequest[A] = Request[Either[String, A], Any]
}
