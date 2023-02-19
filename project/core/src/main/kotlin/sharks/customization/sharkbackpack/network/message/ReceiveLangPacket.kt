package sharks.customization.sharkbackpack.network.message

import com.google.common.io.ByteArrayDataInput
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import shark.bukkitlib.config.LocalizeConfig.Companion.sendLang

class ReceiveLangPacket: IMessage() {

    private lateinit var lang: String
    private lateinit var owner: String
    private lateinit var id: String

    override fun read(i: ByteArrayDataInput) {
        this.lang = i.readUTF()
        this.owner = i.readUTF()
        this.id = i.readUTF()
    }

    override fun message(player: Player) {
        Bukkit.getOnlinePlayers().forEach {
            it.sendLang(lang, owner, id)
        }
    }
}