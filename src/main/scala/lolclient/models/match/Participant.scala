package lolclient.models.`match`

import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Format, JsPath, Json}

case class Player (
  summonerId: String,
  summonerLevel: Int,
  summonerName: String,
  profileIcon: Int,
  puuid: String,
  riotIdName: String,
  riotIdTagline: String,
  participantId: Int
)

case class Champion (
  champExperience: Int,
  champLevel: Int,
  championId: Int,
  championName: String,
  championTransform: Int,
  individualPosition: String,
  lane: String,
  role: String,
  teamPosition: String
)

case class Kda (
  kills: Int,
  deaths: Int,
  assists: Int,
  doubleKills: Int,
  tripleKills: Int,
  quadraKills: Int,
  pentaKills: Int,
  totalMinionsKilled: Int,
  firstBloodAssist: Boolean,
  firstBloodKill: Boolean,
  killingSprees: Int,
  largestKillingSpree: Int
)

case class Items (
  item0: Int,
  item1: Int,
  item2: Int,
  item3: Int,
  item4: Int,
  item5: Int,
  item6: Int,
  consumablesPurchased: Int,
  itemsPurchased: Int
)

case class Vision (
  detectorWardsPlaced: Int,
  sightWardsBoughtInGame: Int,
  visionScore: Int,
  visionWardsBoughtInGame: Int,
  wardsKilled: Int,
  wardsPlaced: Int
)

case class NeutralObjectives (
  baronKills: Int,
  dragonKills: Int,
  neutralMinionsKilled: Int,
  objectivesStolen: Int,
  objectivesStolenAssists: Int
)

case class Turrets (
  firstTowerAssist: Boolean,
  firstTowerKill: Boolean,
  inhibitorKills: Int,
  inhibitorTakedowns: Int,
  inhibitorsLost: Int,
  nexusKills: Int,
  nexusLost: Int,
  nexusTakedowns: Int,
  turretKills: Int,
  turretTakedowns: Int,
  turretsLost: Int
)

case class DamageDealt (
  damageDealtToBuildings: Int,
  damageDealtToObjectives: Int,
  damageDealtToTurrets: Int,
  damageSelfMitigated: Int,
  largestCriticalStrike: Int,
  magicDamageDealt: Int,
  magicDamageDealtToChampions: Int,
  physicalDamageDealt: Int,
  physicalDamageDealtToChampions: Int,
  totalDamageDealt: Int,
  totalDamageDealtToChampions: Int,
  trueDamageDealt: Int,
  trueDamageDealtToChampions: Int
)

case class DamageTaken (
  magicDamageTaken: Int,
  physicalDamageTaken: Int,
  trueDamageTaken: Int,
  totalDamageTaken: Int
)

case class Win (
  gameEndedInEarlySurrender: Boolean,
  gameEndedInSurrender: Boolean,
  win: Boolean,
  teamEarlySurrendered: Boolean
)

case class Gold (
  goldEarned: Int,
  goldSpent: Int
)

case class Abilities (
  spell1Casts: Int,
  spell2Casts: Int,
  spell3Casts: Int,
  spell4Casts: Int
)

case class Heal (
  totalHeal: Int,
  totalHealsOnTeammates: Int,
  totalUnitsHealed: Int
)

case class Shield (
  totalDamageShieldedOnTeammates: Int

)

case class CC (
  timeCCingOthers: Int,
  totalTimeCCDealt: Int
)

case class BasicStats (
  bountyLevel: Int,
  longestTimeSpentLiving: Int,
  totalTimeSpentDead: Int,
  timePlayed: Int
)

case class Participant (
  player: Player,
  champion: Champion,
  kda: Kda
)

object Participant {
  implicit val jsonPlayerFormat : Format[Player] = Json.format[Player]
  implicit val jsonChampionFormat : Format[Champion]= Json.format[Champion]
  implicit val jsonKdaFormat : Format[Kda] = Json.format[Kda]
  implicit val jsonParticipantFormat: Format[Participant]  = (
    (JsPath).format[Player] and
      JsPath.format[Champion] and
      (JsPath).format[Kda]
    )(Participant.apply, unlift(Participant.unapply))
}