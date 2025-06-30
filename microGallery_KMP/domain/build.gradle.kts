plugins {
    `kmp-library-jvm-convention`
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.lbBom))

            implementation(libs.lbCore)

            api(projects.data)
            api(projects.error)
        }
    }
}
