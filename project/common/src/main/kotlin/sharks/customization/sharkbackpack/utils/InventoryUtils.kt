package sharks.customization.sharkbackpack.utils

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.SkullType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import shark.bukkitlib.functions.runAsync
import shark.bukkitlib.utils.StringUtils.toFormat

object InventoryUtils {

    val glassSlotSet = mutableSetOf(1, 2, 4, 9, 10, 11, 12, 13, 14, 15, 16, 17)

    private val glass: ItemStack by lazy {
        val stack = ItemStack(Material.STAINED_GLASS_PANE, 1, 15)
        val meta = stack.itemMeta
        meta.displayName = "§c"
        stack.itemMeta = meta
        stack
    }

    fun createInventory(ownerName: String, title: String, headName: String, contents: Array<ItemStack?>): Inventory {
        val inventory = Bukkit.createInventory(null, 54, title.toFormat(ownerName))
        val skull = ItemStack(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal.toShort())
        val meta = skull.itemMeta
        meta.displayName = headName.toFormat(ownerName)
        skull.itemMeta = meta

        runAsync{
            val sm = skull.itemMeta as SkullMeta
            sm.owningPlayer = Bukkit.getOfflinePlayer(ownerName)
            skull.itemMeta = sm
            inventory.setItem(0, skull)
        }

        inventory.setItem(0, skull)

        inventory.setItem(1, glass)
        inventory.setItem(2, glass)
        inventory.setItem(4, glass)

        for (i in 9 until 18) {
            inventory.setItem(i, glass)
        }

        var index = 0
        for (i in 18 until 54) {
            inventory.setItem(i, contents[index])
            index++
        }
        for (i in 8 downTo 5) {
            inventory.setItem(i, contents[index])
            index++
        }

        inventory.setItem(3, contents[index])

        return inventory
    }

}