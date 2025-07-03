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
    private var _settingsData: SettingsData? = null

     override val settingsData: SettingsData
         get() {
            return if( _settingsData == null)
                    error("trying to get settings data but not fetched from DB")
                else
                    _settingsData !!
        }

    override suspend fun fetchSettingsData() {
        _settingsData = settingsLocal.getSettings()
    }

    override suspend fun getSettingsDataFromDB(): SettingsData {
        return _settingsData ?: settingsLocal.getSettings().also { _settingsData = it }
    }

    override suspend fun clearDB() {
        settingsLocal.clearDB()
    }

    override suspend fun getStatus(): RemoteStatus {
        return remoteStatusDatasource.fetchStatus()
    }

    override suspend fun setSettingsData(settingsUiData: SettingsData) {
        _settingsData = settingsUiData
        settingsLocal.storeSettings(settingsUiData)
    }
}
