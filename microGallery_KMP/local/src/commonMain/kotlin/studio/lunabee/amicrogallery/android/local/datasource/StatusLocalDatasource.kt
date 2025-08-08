package studio.lunabee.amicrogallery.android.local.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.android.local.dao.StatusDao
import studio.lunabee.amicrogallery.android.local.entity.StatusEntity
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.repository.datasource.local.StatusLocal

class StatusLocalDatasource(
    private val statusDao: StatusDao,
) : StatusLocal {

    override fun getStatus(): Flow<RemoteStatus?> {
        return statusDao.getStatus().map { it?.toRemoteStatus() }
    }

    override suspend fun storeStatus(remoteStatus: RemoteStatus) {
        statusDao.store(StatusEntity.fromRemoteStatus(remoteStatus))
    }
}
