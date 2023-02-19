package sharks.customization.sharkbackpack

import shark.bukkitlib.classinject.ClassInject.Companion.inject
import sharks.customization.sharkbackpack.command.MainCommand
import sharks.customization.sharkbackpack.config.RootConfig
import sharks.customization.sharkbackpack.database.IDatabase
import sharks.customization.sharkbackpack.network.NetworkListener

fun init() {

    // 注入
    MainCommand::class.java.inject()
    RootConfig::class.java.inject()
    IDatabase::class.java.inject()
    NetworkListener::class.java.inject()

}