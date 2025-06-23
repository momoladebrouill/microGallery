import studio.lunabee.plugins.StringsProvider

plugins {
    `android-library-convention`
    alias(libs.plugins.lbResources)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "studio.lunabee.amicrogallery.res"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

lbResources {
    provider = StringsProvider.Loco("")
}

dependencies {
    implementation(platform(libs.androidxComposeBom))

    implementation(libs.androidxComposeFoundation)
    api(libs.lbcCore)
    implementation(libs.core.ktx)
}
