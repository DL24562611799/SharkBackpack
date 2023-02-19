package sharks.customization.sharkbackpack.cache

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sharks.customization.sharkbackpack.config.RootConfig
import sharks.customization.sharkbackpack.database.IDatabase
import sharks.customization.sharkbackpack.utils.InventoryUtils
import java.util.concurrent.ConcurrentHashMap

object CacheManager {

    internal val savings = mutableSetOf<String>()
    internal val editors = ConcurrentHashMap<String, PlayerCache>()
    internal val opens = ConcurrentHashMap<String, String>()
    internal val cooldowns = ConcurrentHashMap<String, Long>()
    internal val shows = ConcurrentHashMap<String, String>()

    internal val String.isSaving: Boolean
        get() = savings.contains(this)

    internal fun String.addSaving() {
        savings.add(this)
    }

    internal fun String.removeSaving() {
        savings.remove(this)
    }

    fun HumanEntity.getPlayerCache(): PlayerCache? {
        return opens[this.name]?.let { editors[it] }
    }

    fun open(player: Player, owner: String) {
        if (opens.contains(player.name)) {
            player.closeInventory()
        }

        opens[player.name] = owner

        val op = Bukkit.getPlayerExact(owner)

        val select = if(op != null) {
            val stacks = arrayOfNulls<ItemStack?>(41)
            val inv = op.inventory
            for (i in 0 until 41) {
                stacks[i] = inv.getItem(i)
            }
            stacks
        }else IDatabase.instance.select(owner)

        val inventory = InventoryUtils.createInventory(owner, RootConfig.OPTION_INVENTORY_TITLE, RootConfig.OPTION_HEAD_NAME, select)

        if (!editors.containsKey(owner) && player.name != owner && player.hasPermission("sharkbackpack.admin")) {
            editors[owner] = PlayerCache(owner, inventory)

        }

        player.openInventory(inventory)
    }

}