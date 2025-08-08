plugins {
    `kmp-library-android-convention`
    alias(libs.plugins.androidxRoom)
    alias(libs.plugins.ksp)
}

android {
    namespace = "studio.lunabee.amicrogallery.kmp.local"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidxRoomRuntime)
            implementation(libs.androidxSqliteBundled)

            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.repository)
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidxRoomCompiler)
    add("kspIosArm64", libs.androidxRoomCompiler)
    add("kspIosSimulatorArm64", libs.androidxRoomCompiler)
    add("kspIosX64", libs.androidxRoomCompiler)
}

room {
    schemaDirectory("$projectDir/schemas")
}
