package sharks.customization.sharkbackpack.network

import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import shark.bukkitlib.Active
import shark.bukkitlib.functions.plugin
import shark.bukkitlib.functions.runAsync
import shark.bukkitlib.functions.runSync
import shark.bukkitlib.utils.info
import sharks.customization.sharkbackpack.network.message.IMessage
import sharks.customization.sharkbackpack.network.message.ReceiveLangPacket
import java.util.*

class NetworkListener: PluginMessageListener {

    private val messages: MutableList<Class<out IMessage>> = LinkedList<Class<out IMessage>>()

    init {
        messages.add(ReceiveLangPacket::class.java)
    }

    override fun onPluginMessageReceived(s: String, player: Player, bytes: ByteArray) {
        if (s == "BungeeCord") {
            val `in` = ByteStreams.newDataInput(bytes)
            if (PacketSender.CHANNEL == `in`.readUTF()) {
                val index = `in`.readInt()
                if (index >= messages.size) {
                    return
                }
                val clazz = messages[index]
                try {
                    val iMessage = clazz.newInstance()
                    if (iMessage.async) {
                        runAsync { iMessage.message(player) }
                    } else {
                        runSync { iMessage.message(player) }
                    }
                } catch (e: ReflectiveOperationException) {
                    e.printStackTrace()
                }
            }
        }
    }

    @Active
    private fun enable() {
        val messenger = Bukkit.getServer().messenger
        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord")
        messenger.registerIncomingPluginChannel(plugin, "BungeeCord", this)
        info("")
        info("&a已启动 &fBC &a通信功能~")
    }
}