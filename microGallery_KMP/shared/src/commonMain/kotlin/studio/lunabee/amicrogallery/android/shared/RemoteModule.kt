package studio.lunabee.amicrogallery.android.shared

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import studio.lunabee.microgallery.android.remote.datasource.RemoteStatusDatasourceImpl
import studio.lunabee.microgallery.android.remote.datasource.TreeRemoteDatasourceImpl
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.StatusRemoteDatasource
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

val remoteDatasourceModule = module {
    singleOf(::RootService)
    singleOf(::TreeRemoteDatasourceImpl) bind TreeRemoteDatasource::class
    singleOf(::RemoteStatusDatasourceImpl) bind StatusRemoteDatasource::class
}
