plugins {
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.lbAndroidApplication)
    alias(libs.plugins.lbAndroidFlavors)
}

android {
    namespace = "studio.lunabee.amicrogallery.app"
    defaultConfig {
        versionCode = ProjectConfig.Android.envVersionCode
        versionName = ProjectConfig.Android.envVersionName
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugarJdkLibs)

    implementation(platform(libs.androidxComposeBom))
    implementation(platform(libs.lbBom))

    implementation(libs.androidxComposeMaterial3)
    implementation(libs.androidxNavigationCompose)
    implementation(libs.haze)
    implementation(libs.hazeMaterials)
    implementation(libs.lbCore)
    implementation(libs.lbcPresenter)

    implementation(projects.coreUi)
    implementation(projects.domain)
    implementation(projects.shared)
}
