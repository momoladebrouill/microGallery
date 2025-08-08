package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull

import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.SettingsDataRepository
import studio.lunabee.microgallery.android.repository.datasource.local.SettingsLocal

class SettingsDataRepositoryImpl(
    private val settingsLocal: SettingsLocal,
) : SettingsDataRepository {

    override fun getSettingsData(): Flow<SettingsData> {
        return settingsLocal.getSettings().filterNotNull()
    }

    override suspend fun clearSettingsDB() {
        settingsLocal.storeSettings(SettingsData()) // put default settings data
    }

    override suspend fun setSettingsData(settingsUiData: SettingsData) {
        settingsLocal.storeSettings(settingsUiData)
    }
}
