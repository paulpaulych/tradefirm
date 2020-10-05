typealias NpmTask = com.moowork.gradle.node.npm.NpmTask
plugins {
    id("com.github.node-gradle.node")
}

configurations {
    node {
        version = "14.4.0"
        npmVersion = "6.14.5"
        download = true
        workDir = file("${project.buildDir}/node")
        nodeModulesDir = file("${project.projectDir}")
    }
}

tasks {

    val npmGroup = "node"

    register("clean", Delete::class){
        group = npmGroup
        delete("build")
        delete("node_modules")
    }

    register("npmBuild", NpmTask::class) {
        group = npmGroup
        setArgs(listOf("run", "build"))
        dependsOn(named("npmInstall"))
    }
    register("npmTest", NpmTask::class){
        group = npmGroup
        setArgs(listOf("test"))
        dependsOn(named("npmInstall"))
    }

    register("npmStart", NpmTask::class){
        group = npmGroup
        setArgs(listOf("start"))
        dependsOn(named("npmInstall"))
    }
}

