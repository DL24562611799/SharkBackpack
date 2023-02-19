import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation(project(":project:client"))
    implementation(project(":project:common"))
    implementation(project(":project:core"))
}


tasks {
    withType<ShadowJar> {
        // 设置构建名称
        archiveBaseName.set(rootProject.name)

        archiveClassifier.set("")

        // 重定向包名
        relocate("shark.bukkitlib", "${rootProject.group}.bukkitlib")

        // 构建输出位置
        destinationDirectory.set(file("C:\\Users\\DL\\Desktop\\服务端平台\\paper-1.12.2\\plugins") )
    }
    build {
        dependsOn(shadowJar)
    }
}
