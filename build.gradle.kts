plugins {
//    kotlin("jvm") version "1.9.25"
    kotlin("jvm") version "2.2.21"
//    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.spring") version "2.2.21"
    id("org.springframework.boot") version "3.5.8"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "kg.pm"
version = "0.0.1-SNAPSHOT"
description = "patient-service"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
//    implementation(platform("org.jetbrains.kotlin:kotlin-bom:2.2.21"))
    implementation(platform("org.jetbrains.exposed:exposed-bom:1.0.0-rc-4"))
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter")
    implementation("org.jetbrains.exposed:exposed-java-time")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    implementation("org.jetbrains.kotlin:kotlin-reflect")

//    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

//
//tasks.withType<KotlinCompile> {
//    compilerOptions {
//        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
//        freeCompilerArgs.add("-Xjsr305=strict")
//    }
//}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
