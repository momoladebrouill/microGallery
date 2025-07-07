
plugins {
    `kmp-library-jvm-convention`
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")

            implementation(project.dependencies.platform(libs.lbBom))

            implementation(libs.lbCore)

            api(projects.data)
            api(projects.error)
        }
    }
}
