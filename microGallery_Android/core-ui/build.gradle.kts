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
    implementation(libs.composeMaterial3)
    implementation(libs.navigation.runtime.android)
    implementation(libs.uiTextGoogleFonts)

    api(projects.coreRes)
}
