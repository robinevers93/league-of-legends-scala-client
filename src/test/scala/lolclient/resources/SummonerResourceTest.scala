package lolclient.resources

import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json
import lolclient.models.summoner.Summoner


class SummonerResourceTest extends AnyWordSpec{
  "Sumonner Json handling" must {
    "write JSON correctly" in {
      val summoner = Summoner(
        "VjcWLAUN1EkSZvYJMH_OeG1oPfulStXuWLlBAVCHVkpAo_s",
        "U4faGB30D-5Z3qvpQtTE0LTQf3fweMJ5pqLbfj8_0j2KiaM",
        "Ur5M1914aAHC8MrR5UNPL0wrCz8Fq6-pMPTxaKrNgxGdXjYooCX2mxRl4SjM1MqMtBQ0nadZ24d4wA",
        "Red Flying Robin",
        3478,
        1628893419000L,
        120)
      assert(Json.toJson(summoner) == Json.parse("""{
                                                   |    "id": "VjcWLAUN1EkSZvYJMH_OeG1oPfulStXuWLlBAVCHVkpAo_s",
                                                   |    "accountId": "U4faGB30D-5Z3qvpQtTE0LTQf3fweMJ5pqLbfj8_0j2KiaM",
                                                   |    "puuid": "Ur5M1914aAHC8MrR5UNPL0wrCz8Fq6-pMPTxaKrNgxGdXjYooCX2mxRl4SjM1MqMtBQ0nadZ24d4wA",
                                                   |    "name": "Red Flying Robin",
                                                   |    "profileIconId": 3478,
                                                   |    "revisionDate": 1628893419000,
                                                   |    "summonerLevel": 120
                                                   |}""".stripMargin))
    }
  }

}
