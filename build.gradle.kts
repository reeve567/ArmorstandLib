import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "dev.reeve"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.reeve.dev/repository/maven-releases/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.kotlin.link")
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    
    compileOnly(kotlin("stdlib-jdk8"))
    implementation("com.okkero.skedule:skedule:1.2.6")
    //compileOnly("org.bukkit:craftbukkit:1.19.2-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}