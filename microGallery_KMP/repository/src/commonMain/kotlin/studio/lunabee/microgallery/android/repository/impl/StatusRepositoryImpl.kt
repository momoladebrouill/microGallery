package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.domain.status.StatusRepository
import studio.lunabee.microgallery.android.repository.datasource.local.StatusLocal
import studio.lunabee.microgallery.android.repository.datasource.remote.StatusRemoteDatasource

class StatusRepositoryImpl(
    private val statusLocal: StatusLocal,
    private val remoteStatusDatasource: StatusRemoteDatasource,
) : StatusRepository {

    override fun getStatus(): Flow<RemoteStatus?> {
        return statusLocal.getStatus()
    }

    override suspend fun getStatusFromRemote(): RemoteStatus {
        return remoteStatusDatasource.getStatus()
    }

    override suspend fun setStatus(remoteStatus: RemoteStatus) {
        statusLocal.storeStatus(remoteStatus)
    }
}
