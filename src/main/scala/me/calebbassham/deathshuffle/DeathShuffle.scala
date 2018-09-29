package me.calebbassham.deathshuffle

import java.util.concurrent.ThreadLocalRandom

import me.calebbassham.scenariomanager.api.SimpleScenario
import me.calebbassham.scenariomanager.api.events.ScenarioEvent
import org.bukkit.Location
import org.bukkit.entity.Player

import scala.collection.JavaConverters._
import scala.util.Random

class DeathShuffle extends SimpleScenario() {

  private val rand = new Random()

  override def onScenarioStart(): Unit = {
    scheduleDeathShuffleEvent()
  }

  class DeathShuffleEvent extends ScenarioEvent("Death Shuffle", true) {

    override def run(): Unit = {
      val players: List[Player] = scenarioManager.getGamePlayerProvider.getGamePlayers.asScala.toList

      val shuffleData: List[ShuffleData] = for (player: Player <- players) yield new ShuffleData(player, player.getLocation)
      val shuffled: List[ShuffleData] = rand.shuffle(shuffleData)

      for (i <- players.indices) {
        val player: Player = players(i)
        val sd: ShuffleData = shuffled(i)

        player.teleport(sd.location)
        sendMessage(player, "You were teleported to %s location.".format(ownership(sd.player.getDisplayName)))

        sendMessage(sd.player, "%s was teleported to your location.".format(player.getDisplayName))
      }

      scheduleDeathShuffleEvent()
    }

  }

  class ShuffleData(val player: Player, val location: Location)

  def ownership(subject: String): String = if (!subject.endsWith("s")) subject + "'s" else subject + "'"

  def scheduleDeathShuffleEvent(): Unit = {
    scheduleEvent(new DeathShuffleEvent(), randInt(3 * 60 * 20, 5 * 60 * 20), false)
  }

  def randInt(min: Int, max: Int): Int = ThreadLocalRandom.current().nextInt(min, max + 1)

}
