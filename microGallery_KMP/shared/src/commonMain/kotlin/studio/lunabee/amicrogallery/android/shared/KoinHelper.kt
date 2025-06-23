package studio.lunabee.amicrogallery.android.shared

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.lunabee.amicrogallery.android.local.RoomAppDatabase
import studio.lunabee.amicrogallery.android.local.buildRoomDatabase
import studio.lunabee.amicrogallery.android.remote.CoreHttpClient

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

private val LocalModule: Module = module {
    single<RoomAppDatabase> { buildRoomDatabase(builder = get(), context = Dispatchers.IO) }
}

private val RemoteModule: Module = module {
    single { CoreHttpClient(baseUrl = "https://www.uuidtools.com/api/") }
}

private val RepositoryModule: Module = module {
}

private val DomainModule: Module = module {
}
