package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.settings.SettingsLocal
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.RemoteStatusDatasource

class SettingsRepositoryImpl(
    private val settingsLocal: SettingsLocal,
    private val remoteStatusDatasource: RemoteStatusDatasource,
) : SettingsRepository {
    private var settingsData: SettingsData? = null

    override suspend fun getSettingsData(): SettingsData {
        return settingsData ?: settingsLocal.getSettings().also { settingsData = it }
    }

    override suspend fun clearDB() {
        settingsLocal.clearDB()
    }

    override suspend fun getStatus(): RemoteStatus {
        return remoteStatusDatasource.fetchStatus()
    }

    override suspend fun setSettingsData(settingsUiData: SettingsData) {
        settingsData = settingsUiData
        settingsLocal.storeSettings(settingsUiData)
    }
}
