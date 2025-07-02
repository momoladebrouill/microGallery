package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.settings.SettingsLocal
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsRepositoryImpl(private val settingsLocal: SettingsLocal): SettingsRepository
{
    private val settingsData : SettingsData? = null
    override suspend fun getSettingsData(): SettingsData {
        return settingsLocal.getSettings()
    }
}