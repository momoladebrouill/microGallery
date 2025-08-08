package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.repository.datasource.local.PictureLocal
import studio.lunabee.microgallery.android.repository.datasource.local.SettingsLocal

class SettingsRepositoryImpl(
    private val settingsLocal: SettingsLocal,
    private val pictureLocal: PictureLocal
) : SettingsRepository {
    /*
    override fun getSettingsData(): Flow<SettingsData?> {
        return settingsLocal.getSettings()
    }

    override suspend fun setSettingsData(settingsUiData: SettingsData) {
        settingsLocal.storeSettings(settingsUiData)
    }
  */
    override suspend fun clearPictureDB() {
        pictureLocal.freshStart()
    }

}
