plugins {
    id("io.izzel.taboolib") version "1.54"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    // 添加依赖 格式: groupId:artifactId:版本
    compileOnly("ink.ptms.core:v11200:11200-minimize")
    compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
    // taboo taboolib 插件功能，表示该依赖允许被打入jar包
    taboo("sharks.bukkitlib:bukkitlib:1.0.7")
    // 添加本地依赖项
    compileOnly(fileTree("libs"))
}
