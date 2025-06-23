plugins {
    `kmp-library-android-convention`
    alias(libs.plugins.skie)
}

android {
    namespace = "studio.lunabee.amicrogallery.kmp.shared"
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.koinAndroid)
            api(libs.koinCore)
            api(libs.koinViewmodel)
            implementation(libs.ktorAndroid) // For client injection.
        }

        commonMain.dependencies {
            implementation(libs.androidxRoomRuntime) // For database injection, mandatory.
            implementation(libs.koinCore)

            implementation(projects.domain)
            implementation(projects.local)
            implementation(projects.localRoom)
            implementation(projects.remote)
            implementation(projects.remoteKtor)
            implementation(projects.repository)
        }

        iosMain.dependencies {
            implementation(libs.ktorIos) // For client injection.
        }
    }
}
