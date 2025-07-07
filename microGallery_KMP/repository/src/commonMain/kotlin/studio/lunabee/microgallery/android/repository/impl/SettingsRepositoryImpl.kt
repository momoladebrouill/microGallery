package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.settings.SettingsLocal
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.RemoteStatusDatasource

class SettingsRepositoryImpl(
    private val settingsLocal: SettingsLocal,
    private val remoteStatusDatasource: RemoteStatusDatasource,
) : SettingsRepository {

    override fun getSettingsData(): Flow<SettingsData> {
        return settingsLocal.getSettings()
    }

    override fun getStatus(): Flow<RemoteStatus> {
        return remoteStatusDatasource.fetchStatus()
    }

    override suspend fun setSettingsData(settingsUiData: SettingsData) {
        settingsLocal.storeSettings(settingsUiData)
    }
}
