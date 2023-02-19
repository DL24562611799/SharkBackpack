plugins {
    id("io.izzel.taboolib") version "1.54"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    // 使用 common 模块
    api(project(":project:common"))
    // 添加依赖 格式: groupId:artifactId:版本
    compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("sharks.bukkitlib:bukkitlib:1.0.7")
    // 添加本地依赖项
    compileOnly(fileTree("libs"))
}
