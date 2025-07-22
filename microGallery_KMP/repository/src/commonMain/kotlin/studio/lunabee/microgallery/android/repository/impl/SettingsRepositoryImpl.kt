package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.RemoteStatusDatasource

class SettingsRepositoryImpl(
    private val pictureLocal: PictureLocal,
    private val remoteStatusDatasource: RemoteStatusDatasource,
) : SettingsRepository {

    override fun getStatus(): Flow<RemoteStatus> {
        return remoteStatusDatasource.fetchStatus()
    }

    override suspend fun clearPictureDB() {
        pictureLocal.freshStart()
    }
}
