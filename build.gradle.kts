plugins {
    id("org.gradle.java")
    id("org.gradle.maven-publish")
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}

//子模块配置
subprojects {
    apply<JavaPlugin>()
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        // 使用maven本地仓库
        mavenLocal()
        mavenCentral()
    }

    //公共依赖
    dependencies {
        "compileOnly"(kotlin("stdlib"))
        compileOnly("dl.local.bukkit:ap:3.2.9.6")
        compileOnly("com.google.code.gson:gson:2.8.9")
        compileOnly("com.google.guava:guava:31.0.1-jre")
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.tabooproject.org/repository/releases")
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
            groupId = project.group.toString()
        }
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}