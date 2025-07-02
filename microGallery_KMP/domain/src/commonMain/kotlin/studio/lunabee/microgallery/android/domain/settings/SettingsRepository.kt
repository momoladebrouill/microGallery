package studio.lunabee.microgallery.android.domain.settings

import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsRepository {
    suspend fun getSettingsData() : SettingsData
}