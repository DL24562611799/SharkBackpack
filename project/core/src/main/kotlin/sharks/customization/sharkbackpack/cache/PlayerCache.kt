package sharks.customization.sharkbackpack.cache

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.UUID

class PlayerCache(
    val ownerName: String,
    val showInv: Inventory,
) {

    fun onlineHandler(handler: Player.() -> Unit) {
        Bukkit.getPlayerExact(ownerName)?.let {
            handler.invoke(it)
        }
    }

    fun getContents(): Array<ItemStack?> {
        val itemStacks = arrayOfNulls<ItemStack?>(41)
        var index = 0
        for (i in 18 until this.showInv.size) {
            itemStacks[index] = this.showInv.getItem(i)
            index++
        }
        for (i in 8 downTo 5) {
            itemStacks[index] = this.showInv.getItem(i)
            index++
        }
        itemStacks[index] = this.showInv.getItem(3)
        return itemStacks
    }
}