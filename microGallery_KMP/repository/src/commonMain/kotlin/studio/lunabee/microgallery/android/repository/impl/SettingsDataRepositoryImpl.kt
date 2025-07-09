package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.amicrogallery.settings.SettingsLocal
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.SettingsDataRepository
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.RemoteStatusDatasource

class SettingsDataRepositoryImpl(
    private val settingsLocal: SettingsLocal,
    private val remoteStatusDatasource: RemoteStatusDatasource,
) : SettingsDataRepository {

    override fun getSettingsData(): Flow<SettingsData> {
        return settingsLocal.getSettings()
    }

    override suspend fun clearSettingsDB() {
        settingsLocal.storeSettings(SettingsData()) // put default settings data
    }

    override suspend fun setSettingsData(settingsUiData: SettingsData) {
        settingsLocal.storeSettings(settingsUiData)
    }
}
