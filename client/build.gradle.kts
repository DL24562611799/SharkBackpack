import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation(project(":project:client-enable"))
    implementation(project(":project:common"))
}


tasks {
    withType<ShadowJar> {
        // 设置构建名称
        archiveBaseName.set(rootProject.name)

        archiveClassifier.set("")

        // 重定向包名
        relocate("shark.bukkitlib", "${rootProject.group}.bukkitlib")

        // 构建输出位置
        destinationDirectory.set(file("F:\\services\\build") )
    }
    build {
        dependsOn(shadowJar)
    }
}
