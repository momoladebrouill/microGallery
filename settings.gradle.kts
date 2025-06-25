enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    val artifactoryUsername: String = providers.gradleProperty("artifactory_consumer_username")
        .getOrElse("library-consumer-public")
    val artifactoryPassword: String = providers.gradleProperty("artifactory_consumer_api_key")
        .getOrElse("AKCp8k8PbuxYXoLgvNpc5Aro1ytENk3rSyXCwQ71BA4byg3h7iuMyQ6Sd4ZmJtSJcr7XjwMej")

    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://artifactory.lunabee.studio/artifactory/lunabee-gradle-plugin/") {
            credentials {
                username = artifactoryUsername
                password = artifactoryPassword
            }
            mavenContent {
                releasesOnly()
            }
        }
    }
}

dependencyResolutionManagement {
    val artifactoryUsername: String = providers.gradleProperty("artifactory_consumer_username")
        .getOrElse("library-consumer-public")
    val artifactoryPassword: String = providers.gradleProperty("artifactory_consumer_api_key")
        .getOrElse("AKCp8k8PbuxYXoLgvNpc5Aro1ytENk3rSyXCwQ71BA4byg3h7iuMyQ6Sd4ZmJtSJcr7XjwMej")

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://artifactory.lunabee.studio/artifactory/libs-release-local") {
            credentials {
                username = artifactoryUsername
                password = artifactoryPassword
            }
            mavenContent {
                releasesOnly()
            }
        }
        maven(url = "https://central.sonatype.com/repository/maven-snapshots/") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

plugins {
    id("studio.lunabee.plugins.cache") version "0.9.0"
}

rootProject.name = "microGallery"

include(":app")
include(":core-res")
include(":core-ui")
include(":remote")
include(":remote-ktor")
include(":local")
include(":shared")
include(":domain")
include(":data")
include(":repository")
include(":error")

project(":app").projectDir = File("microGallery_Android/app")
project(":core-res").projectDir = File("microGallery_Android/core-res")
project(":core-ui").projectDir = File("microGallery_Android/core-ui")
project(":remote").projectDir = File("microGallery_KMP/remote")
project(":remote-ktor").projectDir = File("microGallery_KMP/remote-ktor")
project(":local").projectDir = File("microGallery_KMP/local")
project(":domain").projectDir = File("microGallery_KMP/domain")
project(":data").projectDir = File("microGallery_KMP/data")
project(":repository").projectDir = File("microGallery_KMP/repository")
project(":error").projectDir = File("microGallery_KMP/error")
project(":shared").projectDir = File("microGallery_KMP/shared")
