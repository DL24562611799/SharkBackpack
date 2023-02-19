package sharks.customization.sharkbackpack.network.message

import com.google.common.io.ByteArrayDataInput
import org.bukkit.entity.Player

abstract class IMessage {

    var async = false

    abstract fun read(i: ByteArrayDataInput)

    abstract fun message(player: Player)

}