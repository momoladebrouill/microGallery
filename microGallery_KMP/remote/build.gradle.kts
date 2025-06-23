plugins {
    `kmp-library-jvm-convention`
}

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
            implementation(projects.error)
        }
    }
}
