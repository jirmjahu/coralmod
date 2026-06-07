plugins {
    id("java")
    alias(libs.plugins.loom)
}

version = "0.8.0"
group = "net.coralmod.mod"

dependencies {
    minecraft(libs.minecraft)

    implementation(libs.loader)
    implementation(libs.api)

    implementation(libs.gson)
}

tasks.processResources {
    inputs.property("version", version)

    filesMatching("fabric.mod.json") {
        expand("version" to version)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    inputs.property("archivesName", base.archivesName)

    from("LICENSE") {
        rename { "${it}_${base.archivesName.get()}" }
    }
}