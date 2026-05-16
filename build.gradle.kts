plugins {
    `maven-publish`
}

group = "com.diytechy.terra.packs"
version = "1.0.0"

val packZip by tasks.registering(Zip::class) {
    archiveFileName.set("TARTARUS.zip")
    destinationDirectory.set(layout.buildDirectory.dir("artifacts"))

    from(projectDir) {
        exclude { detail ->
            val name = detail.name
            // Exclude hidden files/dirs and build tooling directories
            name.startsWith(".") || name.startsWith("_") ||
            name == "build"
        }
    }
}

publishing {
    repositories {
        mavenLocal()
        maven {
            name = "Repsy"
            url = uri("https://repo.repsy.io/mvn/diytechy/terra-packs")
            credentials {
                username = project.findProperty("repsy.user") as String? ?: System.getenv("REPSY_USERNAME")
                password = project.findProperty("repsy.key") as String? ?: System.getenv("REPSY_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("repsy") {
            groupId = project.group.toString()
            artifactId = "TARTARUS"
            version = project.version.toString()
            artifact(packZip)
        }
    }
}

tasks.named("publish") {
    dependsOn(packZip)
}

tasks.named("publishToMavenLocal") {
    dependsOn(packZip)
}
