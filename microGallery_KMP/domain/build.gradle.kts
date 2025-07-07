
plugins {
    `kmp-library-jvm-convention`
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.lbBom))

            implementation(libs.lbCore)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

            api(projects.data)
            api(projects.error)
        }
    }
}
