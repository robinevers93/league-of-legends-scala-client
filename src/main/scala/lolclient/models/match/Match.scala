package lolclient.models.`match`


import play.api.libs.json.{Format, Json}

case class Match(
  metadata: Metadata,
  info: Info)

object Match {
  implicit val format: Format[Match] = Json.format[Match]
}