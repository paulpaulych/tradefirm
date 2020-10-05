plugins {
    java
    idea
    id("org.springframework.boot")
}

dependencies {
    val springBootVersion = "2.2.6.RELEASE"
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$springBootVersion")
    implementation("org.postgresql:postgresql:42.2.8")
}

tasks {
//    bootRun{
//        systemProperties = System.properties
//    }
}


