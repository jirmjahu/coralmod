plugins {
    id ("java")
	alias(libs.plugins.loom)
}

version = "0.0.1"
group = "net.coralmod.mod"

repositories {

}

dependencies {
    minecraft(libs.minecraft)
    mappings(libs.mappings)
    modImplementation(libs.loader)
    modImplementation(libs.api)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand("version" to project.version)
    }
}