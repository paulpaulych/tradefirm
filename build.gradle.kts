import org.jetbrains.kotlin.utils.addToStdlib.safeAs

buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")
    }
}

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.8.RELEASE"  apply false
    id("com.github.node-gradle.node") version "2.2.4" apply false
    id("org.sonarqube") version "3.0" apply true
    id("jacoco")
}

allprojects{
    repositories {
        mavenCentral()
        mavenLocal()
    }
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
        set("sonarUrl", "http://84.237.50.237:9000")
    }
}

//        'server/src/main/kotlin/paulpaulych/tradefirm/application/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/area/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/dbcommon/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/delivery/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/order/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/product/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/sale/**',
//        'server/src/main/kotlin/paulpaulych/tradefirm/salespoint/**'

tasks{
    register("helloNode"){
        doLast{
            println("hello: ${project(":frontend")
                    .container<com.moowork.gradle.node.NodeExtension>()}")
        }
    }
}

sonarqube {

    properties{
        properties(mapOf(
                "sonar.sourceEncoding" to "UTF-8",
                "sonar.projectKey" to "tradefirm",
                "sonar.projectName" to "tradefirm",
                "sonar.host.url" to "http://84.237.50.237:9000",
                "sonar.login" to "97923e51ec08b50140881779b189514cf99a3cc4",
                "sonar.logging.level" to "trace",
                "sonar.skipPackageDesign" to "true",
                "sonar.skipDesign" to "true",
                "sonar.coverage.exclusions" to exclusionList
        ))
    }
}

val exclusionList = arrayOf(
        "frontend/**",
        "server/src/main/kotlin/paulpaulych/tradefirm/config/graphql/**",
        "server/src/main/kotlin/paulpaulych/tradefirm/config/orm/**",
        "server/src/main/kotlin/paulpaulych/tradefirm/analytics/**")
        .joinToString(",")


