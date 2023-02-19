package sharks.customization.sharkbackpack.command

import org.bukkit.command.CommandSender
import shark.bukkitlib.Command
import shark.bukkitlib.command.BaseCommand
import shark.bukkitlib.config.LocalizeConfig.Companion.sendLang

@Command
class MainCommand: BaseCommand() {

    override val name = "sharkbackpack"
    override val aliases = arrayOf("sb")

    init {
        addCommands(
            ReloadCommand(),
            ShowCommand(),
            OpenCommand(),
        )
    }

    override fun onCommand(sender: CommandSender, args: Array<String>): Boolean {
        sender.sendLang("COMMAND_HELP_SHOW")
        if (sender.hasPermission("sharkbackpack.command")) {
            sender.sendLang("COMMAND_HELP_RELOAD")
            sender.sendLang("COMMAND_HELP_OPEN")
        }
        return true
    }

    override fun onTabComplete(sender: CommandSender, args: Array<String>): List<String>? {
        return null
    }
}