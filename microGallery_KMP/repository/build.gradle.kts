plugins {
    `kmp-library-jvm-convention`
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
            implementation(projects.local)
        }
    }
}
