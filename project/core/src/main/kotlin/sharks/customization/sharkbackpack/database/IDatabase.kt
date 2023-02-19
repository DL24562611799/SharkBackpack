package sharks.customization.sharkbackpack.database

import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import shark.bukkitlib.Active
import shark.bukkitlib.ActiveType
import shark.bukkitlib.Scheduler
import shark.bukkitlib.SubEventHandler
import shark.bukkitlib.config.LocalizeConfig.Companion.sendLang
import shark.bukkitlib.functions.submit
import sharks.customization.sharkbackpack.cache.CacheManager
import sharks.customization.sharkbackpack.cache.CacheManager.addSaving
import sharks.customization.sharkbackpack.cache.CacheManager.getPlayerCache
import sharks.customization.sharkbackpack.cache.CacheManager.isSaving
import sharks.customization.sharkbackpack.cache.CacheManager.removeSaving
import sharks.customization.sharkbackpack.event.PlayerEvents
import sharks.customization.sharkbackpack.utils.InventoryUtils
import java.util.UUID
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

interface IDatabase {

    fun update(playerName: String, contents: Array<ItemStack?>, callback: Boolean.() -> Unit = {})

    fun select(playerName: String): Array<ItemStack?>

    fun isExist(playerName: String): Boolean

    companion object {

        val instance: IDatabase by lazy{ YamlDatabase() }

        internal val executor: ExecutorService by lazy { Executors.newCachedThreadPool() }
        internal val backpackLocks = mutableSetOf<UUID>()

        @SubEventHandler
        private fun onJoin(e: PlayerJoinEvent) {
            val player = e.player
            backpackLocks.add(e.player.uniqueId)
            submit(delay = 5, async = true) {
                if (player.isOnline && instance.isExist(player.name)) {
                    val inv = player.inventory
                    instance.select(player.name).forEachIndexed { index, itemStack ->
                        inv.setItem(index, itemStack)
                    }
                }
                backpackLocks.remove(e.player.uniqueId)
            }
        }

        @SubEventHandler
        private fun onQuit(e: PlayerQuitEvent) {
            PlayerEvents.Release(e.player).callEvent()
        }

        @SubEventHandler
        private fun onKick(e: PlayerKickEvent) {
            PlayerEvents.Release(e.player).callEvent()
        }

        @SubEventHandler
        private fun onRelease(e: PlayerEvents.Release) {
            CacheManager.shows.remove(e.player.name)
            CacheManager.cooldowns.remove(e.player.name)
            CacheManager.opens.remove(e.player.name)
            e.player.saveBackpack()
        }

        @SubEventHandler
        private fun onCraftingClose(e: InventoryCloseEvent) {
            if (e.inventory.title == "container.crafting") {
                e.player.saveBackpack()
            }
        }

        @SubEventHandler
        private fun onShowClose(e: InventoryCloseEvent) {
            val cache = e.player.getPlayerCache()
            if (cache != null) {
                CacheManager.editors.remove(cache.ownerName)
                cache.onlineHandler {
                    val inv = inventory
                    cache.getContents().forEachIndexed { index, itemStack ->
                        inv.setItem(index, itemStack)
                    }
                }

                if (!cache.ownerName.isSaving) {
                    cache.ownerName.addSaving()
                    instance.update(cache.ownerName, cache.getContents()) {cache.ownerName.removeSaving()}
                }
            }
        }

        @SubEventHandler
        private fun onShowClose2(e: InventoryCloseEvent) {
            CacheManager.opens.remove(e.player.name)
        }

        @SubEventHandler
        private fun onClick2(e: InventoryClickEvent) {
            if (backpackLocks.contains(e.whoClicked.uniqueId)) {
                e.isCancelled = true
                e.whoClicked.sendLang("BACKPACK_LOADING")
            }
        }

        @SubEventHandler
        private fun onClick(e: InventoryClickEvent) {
            if (e.inventory.size == 54) {
                val cache = CacheManager.opens[e.whoClicked.name]
                if (cache != null) {
                    if (e.rawSlot == 0 || InventoryUtils.glassSlotSet.contains(e.rawSlot)) {
                        e.isCancelled = true
                    }else {
                        if (e.whoClicked.name == cache || !e.whoClicked.hasPermission("sharkbackpack.admin")) {
                            e.isCancelled = true
                        }else if (!CacheManager.editors.containsKey(cache)) {
                            e.isCancelled = true
                        }
                    }
                }
            }
        }

        @Active(type = ActiveType.STOP)
        private fun disable() {
            save()
            executor.shutdown()
        }

        @Scheduler(period = 200, async = true)
        private fun save() {
            Bukkit.getOnlinePlayers().forEach {
                it.saveBackpack()
            }
        }

        private fun HumanEntity.saveBackpack() {
            if (!name.isSaving) {
                name.addSaving()
                instance.update(name, inventory.contents) { name.removeSaving() }
            }
        }
    }
}