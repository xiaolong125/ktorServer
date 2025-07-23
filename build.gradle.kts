plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {

    // Ktor核心
    implementation("io.ktor:ktor-server-core-jvm:3.2.2")
    implementation("io.ktor:ktor-server-netty-jvm:3.2.2")
    implementation("io.ktor:ktor-server-content-negotiation:3.2.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.2")

    implementation("io.ktor:ktor-server-openapi:3.2.2")
    implementation("io.ktor:ktor-server-swagger:3.2.2")

    // ✅ Exposed ORM + MySQL
    implementation("org.jetbrains.exposed:exposed-core:0.45.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.45.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.45.0")
    implementation("com.mysql:mysql-connector-j:9.3.0")

    // ✅ 日志
    implementation("ch.qos.logback:logback-classic:1.5.13")

    // ✅ Swagger / OpenAPI
    implementation("io.ktor:ktor-server-openapi:3.2.2")
    implementation("io.ktor:ktor-server-swagger:3.2.2")

    implementation("dev.forst:ktor-openapi-generator:0.6.1")


}


application {
    mainClass.set("MainKt")
}

kotlin {
    jvmToolchain(17)
}
