package studio.lunabee.amicrogallery.android.shared

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.lunabee.microgallery.android.remote.CoreHttpClient

object KoinHelper {
    fun init(block: KoinApplication.() -> Unit) {
        startKoin {
            modules(
                PlatformSpecificModule,
                LocalModule,
                RemoteModule,
                RepositoryModule,
                DomainModule,
            )
            block()
        }
    }
}

expect val PlatformSpecificModule: Module

private val RemoteModule: Module = module {
    single { CoreHttpClient(baseRemoteUrl = "http://92.150.239.130") }
}

private val RepositoryModule: Module = module {
}

private val DomainModule: Module = module {
}
