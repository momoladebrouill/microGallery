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
    implementation(libs.composeMaterial3)
    implementation("io.coil-kt.coil3:coil-network-ktor3:3.2.0")
    implementation(libs.ktorAndroid)
    implementation(libs.navigationRuntimeAndroid)
    implementation(libs.uiTextGoogleFonts)

    api(projects.coreRes)
}
