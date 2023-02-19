package sharks.customization.sharkbackpack.config

import shark.bukkitlib.Config
import shark.bukkitlib.config.YamlConfig
import shark.bukkitlib.utils.ColorUtils.color

@Config
class RootConfig: YamlConfig("config.yml") {

    override fun load() {
        val c = this.yaml
        DATABASE_TYPE = c.getString("database.type")
        DATABASE_STORAGE = c.getString("database.storage", "")

        OPTION_SHOW_COOLDOWN = c.getInt("options.cooldown", 10)
        OPTION_HEAD_NAME = c.getString("options.head", "{0}").color()
        OPTION_INVENTORY_TITLE = c.getString("options.title", "{0}").color()
    }

    companion object {

        var OPTION_SHOW_COOLDOWN: Int = 10
        lateinit var OPTION_HEAD_NAME: String
        lateinit var OPTION_INVENTORY_TITLE: String

        lateinit var DATABASE_TYPE: String
        lateinit var DATABASE_STORAGE: String
    }
}