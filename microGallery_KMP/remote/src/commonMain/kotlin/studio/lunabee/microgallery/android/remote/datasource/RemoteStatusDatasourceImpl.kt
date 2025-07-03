package studio.lunabee.microgallery.android.remote.datasource

import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.RemoteStatusDatasource

class RemoteStatusDatasourceImpl(
    private val rootService: RootService,
) : RemoteStatusDatasource {

    override suspend fun fetchStatus(): RemoteStatus {
        return rootService.fetchStatus().toRemoteStatus()
    }
}
