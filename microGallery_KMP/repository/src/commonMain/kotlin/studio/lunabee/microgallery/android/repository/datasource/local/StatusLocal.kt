package studio.lunabee.microgallery.android.repository.datasource.local

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus

interface StatusLocal {
    fun getStatus(): Flow<RemoteStatus?>
    suspend fun storeStatus(remoteStatus: RemoteStatus)
}
