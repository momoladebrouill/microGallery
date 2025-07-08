package studio.lunabee.microgallery.android.domain.settings

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsRepository {
    fun getSettingsData(): Flow<SettingsData?>
    suspend fun setSettingsData(settingsUiData: SettingsData)
}
