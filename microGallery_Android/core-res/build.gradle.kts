import studio.lunabee.plugins.StringsProvider

plugins {
    `android-library-convention`
    alias(libs.plugins.lbResources)
}

android {
    namespace = "studio.lunabee.amicrogallery.res"
}

lbResources {
    provider = StringsProvider.Loco("")
}

dependencies {
    implementation(platform(libs.androidxComposeBom))

    implementation(libs.androidxComposeFoundation)
    implementation(libs.coreKtx)
    api(libs.lbcCore)
}
