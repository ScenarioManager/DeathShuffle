package me.calebbassham.deathshuffle

import me.calebbassham.scenariomanager.api.ScenarioManagerInstance
import org.bukkit.plugin.java.JavaPlugin

class DeathShufflePlugin extends JavaPlugin {

  override def onEnable(): Unit = {
    ScenarioManagerInstance.getScenarioManager.registerScenario(new DeathShuffle(), this)
  }

}
