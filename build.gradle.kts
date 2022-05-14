plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version "2.10.1"
}

group = "org.echoosx"
version = "1.2.1"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    mavenCentral()
}
dependencies {
    implementation("org.json:json:20211205")
    implementation("org.jsoup:jsoup:1.14.3")
    implementation("org.quartz-scheduler:quartz:2.3.2")
}