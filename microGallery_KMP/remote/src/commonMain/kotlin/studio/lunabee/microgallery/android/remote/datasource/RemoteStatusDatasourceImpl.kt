package studio.lunabee.microgallery.android.remote.datasource

import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.StatusRemoteDatasource

class RemoteStatusDatasourceImpl(
    private val rootService: RootService,
) : StatusRemoteDatasource {

    override suspend fun getStatus(): RemoteStatus {
        return rootService.fetchStatus().toRemoteStatus()
    }
}
