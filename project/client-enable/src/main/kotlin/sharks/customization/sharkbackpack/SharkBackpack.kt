package sharks.customization.sharkbackpack

import com.luxun.mslibrary.license.api.LicensePlugin
import shark.bukkitlib.functions.plugin
import shark.bukkitlib.plugin.KotlinPlugin
import java.io.File

object SharkBackpack: KotlinPlugin(), LicensePlugin {

    override val pluginId: String = plugin.name

    override val pluginVersion: String = plugin.description.version

    override fun disablePlugin() {
        onDisable()
    }

    override fun getDataFolder(): File {
        return plugin.dataFolder
    }

    override fun info(p0: String?) {
        info(p0)
    }

    override fun onLoad() {
        autoInjects()
    }

    override fun onEnable() {
        loads()
        reload()
        shark.bukkitlib.utils.info("&a插件已启动成功~")
    }

    override fun onDisable() {
        stops()
    }

}