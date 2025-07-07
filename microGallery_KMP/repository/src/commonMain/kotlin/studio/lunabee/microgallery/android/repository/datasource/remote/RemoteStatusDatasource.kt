package studio.lunabee.microgallery.android.repository.datasource.remote

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus

interface RemoteStatusDatasource {
    fun fetchStatus(): Flow<RemoteStatus>
}
