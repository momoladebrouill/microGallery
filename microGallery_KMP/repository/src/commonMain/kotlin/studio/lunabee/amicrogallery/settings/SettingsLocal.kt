package studio.lunabee.amicrogallery.settings

import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsLocal {
    suspend fun getSettings() : SettingsData
    suspend fun storeSettings(settingsData: SettingsData)
}