package studio.lunabee.microgallery.android.repository.datasource.local

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsLocal {
    fun getSettings(): Flow<SettingsData?>
    suspend fun storeSettings(settingsData: SettingsData)
}
