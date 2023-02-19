package sharks.customization.sharkbackpack.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import shark.bukkitlib.command.BaseCommand
import shark.bukkitlib.config.LocalizeConfig.Companion.sendLang
import sharks.customization.sharkbackpack.cache.CacheManager
import sharks.customization.sharkbackpack.config.RootConfig
import sharks.customization.sharkbackpack.network.PacketSender
import java.util.UUID

class ShowCommand: BaseCommand() {

    override val name = "show"
    override val aliases = arrayOf("s")

    override fun onCommand(sender: CommandSender, args: Array<String>): Boolean {
        val cd = CacheManager.cooldowns.getOrDefault(sender.name, 0L)
        val timeMillis = System.currentTimeMillis()
        if (cd > timeMillis) {
            sender.sendLang("SHOW_COOLDOWN", ((cd - timeMillis) / 1000 + 1))
            return true
        }
        CacheManager.cooldowns[sender.name] = RootConfig.OPTION_SHOW_COOLDOWN * 1000L + timeMillis
        val id = sender.name + ":" + UUID.randomUUID()
        CacheManager.shows[sender.name] = id
        Bukkit.getOnlinePlayers().forEach {
            it.sendLang("SHOW_MESSAGE", sender.name, id)
        }
        PacketSender.sendLang("SHOW_MESSAGE", sender.name, id)
        return true
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String>? {
        return null
    }
}