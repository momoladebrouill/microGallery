package studio.lunabee.microgallery.android.repository.datasource.remote

import studio.lunabee.microgallery.android.data.RemoteStatus

interface RemoteStatusDatasource {
    suspend fun fetchStatus(): RemoteStatus
}
