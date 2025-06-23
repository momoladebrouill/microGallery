

plugins {
    `kmp-library-jvm-convention`
    alias(libs.plugins.kotlinxSerialization)
}

/*
android {
    namespace = "studio.lunabee.amicrogallery.kmp.remote"
}
*/

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.lbBom))

            implementation(libs.kotlinxSerializationJson)
            implementation(libs.lbCore)
            implementation(libs.lbKtorCore)
            implementation(libs.lbKtorJson)
            implementation(libs.lbKtorKermit)

            implementation(projects.data)
            implementation(projects.repository)
            implementation(projects.domain)
            implementation(projects.error)
        }
    }
}





