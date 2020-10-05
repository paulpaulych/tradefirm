plugins {
    kotlin("jvm") version "1.3.71" apply false
    id("org.springframework.boot") version "2.2.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.8.RELEASE"  apply false
    id("com.github.node-gradle.node") version "2.2.4" apply false
}

group = "paulpaulych.tradefirm"
version = "1.0-SNAPSHOT"

allprojects{
    repositories {
        mavenCentral()
        mavenLocal()
    }
    extra.apply{
        set("logbackClassicVersion", "1.3.0-alpha4")
        set("lombokVersion", "1.18.10")
        set("commonsCliVersion", "1.2")
        set("springBootVersion", "2.2.6.RELEASE")
        set("postgresDriverVersion", "42.2.8")
        set("simpleOrmVersion", "1.2.0")
        set("commonUtilsVersion", "1.0.1")
        set("kotestVersion", "4.0.5")
        set("graphqlKotlinVersion", "3.1.0")
        set("jacksonVersion", "2.9.0")
        set("nodeGradleVersion", "2.2.4")
    }
}
