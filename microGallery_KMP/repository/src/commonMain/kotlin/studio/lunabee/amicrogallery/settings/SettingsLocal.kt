package studio.lunabee.amicrogallery.settings

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsLocal {
    fun getSettings(): Flow<SettingsData>
    suspend fun storeSettings(settingsData: SettingsData)
}
