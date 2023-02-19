package sharks.customization.sharkbackpack.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import shark.bukkitlib.command.BaseCommand
import shark.bukkitlib.config.LocalizeConfig.Companion.sendLang
import shark.bukkitlib.functions.runAsync
import sharks.customization.sharkbackpack.cache.CacheManager
import java.util.UUID

class OpenCommand: BaseCommand() {

    override val name = "open"
    override val aliases = arrayOf("o")

    override fun onCommand(sender: CommandSender, args: Array<String>): Boolean {
        if (args.isNotEmpty()) {
            var owner: String? = null
            if (args[0].contains(":")) {
                owner = args[0].substring(0, args[0].indexOf(":"))
            }else if (sender.hasPermission("sharkbackpack.command")){
                owner = args[0]
            }
            if (owner != null) {
                runAsync{
                    CacheManager.open(sender as Player, owner)
                }
            }
            return true
        }
        sender.sendLang("COMMAND_HELP_OPEN")
        return false
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String>? {
        if (sender.hasPermission("sharkbackpack.command")) {
            when(args.size) {
                1 -> {
                    return StringUtil.copyPartialMatches(args[0], mutableListOf<String>().apply {
                        Bukkit.getOfflinePlayers().forEach {
                            this.add(it.name)
                        }
                    }, mutableListOf())
                }
            }
        }
        return null
    }
}