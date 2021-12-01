package lolclient.models

import lolclient.models.`match`.Match
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec
import play.api.libs.json.Json


class MatchTest extends AnyWordSpec{
  "Match Json handling" must {
    "read JSON correctly" in {
      val path = getClass.getClassLoader.getResource("src/test/Resources/matchData.json").getPath
      val source = scala.io.Source.fromFile(path)
      val lines = try source.mkString finally source.close()
      val matchData = Json.parse(lines)
      val parsedMatchData = matchData.as[Match]

      parsedMatchData.info.gameMode shouldBe "CLASSIC"
      parsedMatchData.metadata.matchId shouldBe "EUW1_5424672772"
      parsedMatchData.info.teams.head.win shouldBe true
      parsedMatchData.info.teams.last.win shouldBe false
      parsedMatchData.info.participants.head.champion.championName shouldBe "Quinn"
      parsedMatchData.info.participants.head.kda.kills shouldBe 10
    }
  }

}
