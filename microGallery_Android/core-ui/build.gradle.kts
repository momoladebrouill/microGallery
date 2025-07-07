plugins {
    `android-library-convention`
}

lbAndroidLibrary {
    android {
        withCompose = true
    }
}

android {
    namespace = "studio.lunabee.amicrogallery.core.ui"
}

dependencies {
    implementation(platform(libs.androidxComposeBom))

    implementation(libs.activity.ktx)
    implementation(libs.androidxComposeFoundation)
    implementation(libs.androidxComposeMaterial3)
    implementation(libs.androidxComposeUi)
    api(libs.coilCompose)
    implementation(libs.coilNetwork)
    implementation(libs.composeMaterial3)
    implementation(libs.haze)
    implementation(libs.hazeMaterials)
    implementation(libs.koinCore)
    implementation(libs.ktorAndroid)
    implementation(libs.navigationRuntimeAndroid)
    implementation(libs.uiTextGoogleFonts)

    api(projects.coreRes)
    implementation(projects.data)
    implementation(projects.domain)
}
