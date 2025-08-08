package studio.lunabee.microgallery.android.domain.status

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus

interface StatusRepository {
    fun getStatus(): Flow<RemoteStatus?>
    suspend fun getStatusFromRemote(): RemoteStatus
    suspend fun setStatus(remoteStatus: RemoteStatus)
}
