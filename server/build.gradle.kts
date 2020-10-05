plugins {
    java
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.allopen") version "1.3.71"
    id("org.springframework.boot")
}

apply(plugin = "kotlin-spring")

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-serialization")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.14.0")

    implementation(files("../libs/simple-orm-core-1.2.0.jar"))
    implementation(files("../libs/common-utils-1.0.1.jar"))
    implementation("io.mockk:mockk:1.9.3")
    implementation("cglib:cglib:3.3.0")

    val jacksonVersion = "2.9.0"
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    val springBootVersion = "2.2.6.RELEASE"
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-jetty:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-security:$springBootVersion")
    implementation("org.springframework.security:spring-security-jwt:1.1.0.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")

    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
    implementation("org.postgresql:postgresql:42.2.8");

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.1.10")
    testImplementation("io.kotlintest:kotlintest-extensions-spring:3.1.10")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.1")

    implementation("com.h2database:h2:1.4.200")

    implementation("com.expediagroup:graphql-kotlin-spring-server:3.1.0"){
        exclude(group = "com.fasterxml.jackson.module")
    }
    implementation("com.graphql-java:graphql-java-extended-scalars:1.0")
    testImplementation("io.projectreactor:reactor-test:3.3.6.RELEASE")

}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    allOpen{
        annotation("paulpaulych.utils.Open")
        annotation("org.springframework.context.annotation.Configuration")
    }

    test {
        useJUnitPlatform()
    }

    springBoot{
        mainClassName = "paulpaulych.tradefirm.AppKt"
    }
}
