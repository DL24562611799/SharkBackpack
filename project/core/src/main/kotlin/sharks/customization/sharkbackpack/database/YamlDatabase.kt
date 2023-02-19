package sharks.customization.sharkbackpack.database

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import shark.bukkitlib.functions.plugin
import shark.bukkitlib.functions.runSync
import shark.bukkitlib.utils.NumberUtils
import shark.bukkitlib.utils.StringUtils.isNotEmpty
import sharks.customization.sharkbackpack.config.RootConfig
import sharks.customization.sharkbackpack.utils.ItemSerialize
import java.io.File
import java.io.IOException

class YamlDatabase: IDatabase {

    private val folder: File by lazy {
        if (RootConfig.DATABASE_STORAGE.isNotEmpty()) {
            File(RootConfig.DATABASE_STORAGE, plugin.name)
        }else {
            File(plugin.dataFolder, "data")
        }
    }

    override fun update(playerName: String, contents: Array<ItemStack?>, callback: Boolean.() -> Unit) {
        IDatabase.executor.execute {
            val file = playerName.getNewFile()
            val yaml = YamlConfiguration()
            contents.forEachIndexed { index, itemStack ->
                yaml.set(index.toString(), ItemSerialize.serialize(itemStack))
            }
            try {
                yaml.save(file)
                callback.invoke(true)
            }catch (e: IOException) {
                callback.invoke(false)
            }
        }
    }

    override fun select(playerName: String): Array<ItemStack?> {
        val contents = arrayOfNulls<ItemStack?>(41)
        playerName.getNewFile().apply {
            if (exists()) {
                val yaml = YamlConfiguration.loadConfiguration(this)
                yaml.getKeys(false).forEach {
                    contents[NumberUtils.toInt(it)] = ItemSerialize.deserialize(yaml.getString(it))
                }
            }
        }
        return contents
    }

    override fun isExist(playerName: String): Boolean {
        return playerName.getNewFile().exists()
    }

    private fun String.getNewFile(): File = File(folder, "$this.yml")

}