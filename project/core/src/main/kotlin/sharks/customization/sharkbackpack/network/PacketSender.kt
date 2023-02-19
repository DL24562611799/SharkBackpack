package sharks.customization.sharkbackpack.network

import com.google.common.io.ByteArrayDataOutput
import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import shark.bukkitlib.functions.plugin
import java.util.UUID

object PacketSender {

    const val CHANNEL = "SharkMail:Main"

    fun sendLang(lang: String, owner: String, id: String) {
        sendServerMessage(0) {
            writeUTF(lang)
            writeUTF(owner)
            writeUTF(id)
        }
    }

    private fun sendServerMessage(index: Int, packet: ByteArrayDataOutput.() -> Unit) {
        plugin.server.sendPluginMessage(plugin, "BungeeCord", toByteArrays(index, packet))
    }

    private fun sendPlayerMessage(p: Player, index: Int, packet: ByteArrayDataOutput.() -> Unit) {
        p.sendPluginMessage(plugin, "BungeeCord", toByteArrays(index, packet))
    }

    private fun toByteArrays(index: Int, packet: ByteArrayDataOutput.() -> Unit): ByteArray? {
        val out = ByteStreams.newDataOutput()
        out.writeUTF("Forward")
        out.writeUTF("ALL")
        out.writeUTF(CHANNEL)
        out.writeInt(index)
        packet.invoke(out)
        return out.toByteArray()
    }
}