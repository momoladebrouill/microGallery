package studio.lunabee.amicrogallery.android.shared

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import studio.lunabee.microgallery.android.remote.CoreHttpClient

object KoinHelper {
    fun init(block: KoinApplication.() -> Unit) {
        startKoin {
            modules(
                LocalModule,
                RemoteModule,
                PlatformSpecificModule,

            )
            block()
        }
    }
}

expect val PlatformSpecificModule: Module

private val RemoteModule: Module = module {
    singleOf(::CoreHttpClient)
}
