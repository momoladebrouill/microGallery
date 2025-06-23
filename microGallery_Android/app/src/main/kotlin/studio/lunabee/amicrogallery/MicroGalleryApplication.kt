package studio.lunabee.amicrogallery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import studio.lunabee.amicrogallery.android.shared.KoinHelper
import studio.lunabee.amicrogallery.di.presentersModule

class MicroGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinHelper.init {
            androidLogger(level = Level.INFO)
            androidContext(this@MicroGalleryApplication)

            modules(presentersModule)
        }
    }
}
