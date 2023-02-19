package sharks.customization.sharkbackpack.command

import org.bukkit.command.CommandSender
import shark.bukkitlib.command.BaseCommand
import shark.bukkitlib.config.LocalizeConfig.Companion.sendLang
import shark.bukkitlib.plugin.KotlinPlugin

class ReloadCommand: BaseCommand() {

    override val name = "reload"
    override val aliases = arrayOf("r")
    override val permissionNode = "sharkbackpack.command"

    override fun onCommand(sender: CommandSender, args: Array<String>): Boolean {
        KotlinPlugin.reload()
        sender.sendLang("COMMAND_RELOAD")
        return true
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String>? {
        return null
    }
}